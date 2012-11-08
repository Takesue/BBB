package com.ict.apps.bobb.bobbactivity;

import java.util.ArrayList;
import java.util.Arrays;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 虫キット一覧画面クラス
 *
 */
public class BeetleKitListActivity extends BaseActivity {
	
	private BeetleKitFactory factory = new BeetleKitFactory(this);

	private ArrayList<BeetleKit> beetleKitList = new ArrayList<BeetleKit>();
	private ArrayList<View> beetleKitViewList = new ArrayList<View>();
	// コンテキストで選択されたアイテム
	private BeetleKit contextSelectedKit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_beetlekitlist);
		
		this.getAlreadySettingKit();
		
		this.createListItems();

	}


	/**
	 * 虫キットの一覧のアイテムを生成して、Listに追加する。
	 */
	private void createListItems() {
		ArrayList<BeetleKit> kitlist = factory.getBeetleKit(BeetleKitFactory.KitType.NORMAL);
		ArrayList<BeetleKit> kitlistSpe = factory.getBeetleKit(BeetleKitFactory.KitType.SPECIAL);
		// 虫キットリストに特殊カードを追加
		kitlist.addAll(kitlistSpe);

		// 表示アイテム設定先の取得
		LinearLayout vgroup = (LinearLayout)this.findViewById(R.id.beetle_kit_list);

		int cnt = kitlist.size();
		for (int i = 0; i < cnt; i++) {
			// 虫キット情報を表示用アイテムに設定
			vgroup.addView(this.addBeeetleKitList(kitlist.get(i)));
			
		}
		
		// 枚数表示
		TextView tv = (TextView)this.findViewById(R.id.beetle_kitlist_title);
		tv.setText("虫キット一覧" + "  (" + cnt + "枚)");
	}
	
	/**
	 * 虫キットの一覧のアイテムをクリアする
	 */
	private void clearListItems(){
		
		// 表示アイテム設定先の取得
		LinearLayout vgroup = (LinearLayout)this.findViewById(R.id.beetle_kit_list);
		
		// Viewを全削除
		vgroup.removeAllViews();
		
		// 一覧数のカウントをリセット
		
		// コンテキスト情報クリア
		this.beetleKitList.clear();
		this.beetleKitViewList.clear();
		this.contextSelectedKit = null;
	}
	

	/**
	 * アイコン表示付きリストに虫キット情報追加
	 * @param mInflater
	 * @param vgroup
	 * @param kit
	 */
	private View addBeeetleKitList(BeetleKit kit) {
		
		// リストするアイテム表示用定義をViewとして取得する
		View view = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.beetle_kit_list_item_with_icon, null);
		
		// 名前設定
		((TextView)view.findViewById(R.id.kit_name)).setText(kit.getName());
		// アイコン画像設定
		ImageView image = (ImageView)view.findViewById(R.id.kiticon);
		image.setImageResource(kit.getImageResourceId(this));
		
		if (kit.getType() == 1) {
			// 一般虫キットカードの場合
			// 攻撃値設定
			((TextView)view.findViewById(R.id.kit_attack)).setText("攻  ：  " + kit.getAttack());
			// 守備値設定
			((TextView)view.findViewById(R.id.kit_defense)).setText("守  ：  " + kit.getDefense());
		}
		else if (kit.getType() == 2){
			// 特殊虫キットの場合
			// 効果説明設定
			((TextView)view.findViewById(R.id.kit_attack)).setText("効果 ：  " + kit.getEffect());
			// 効果説明内容
			((TextView)view.findViewById(R.id.kit_defense)).setText("  ");
		}
		
		// リスナーの設定
		final BeetleKit bk = kit;
		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// クリック時の処理
				Intent intent = new Intent(BeetleKitListActivity.this, BeetleKitDetailActivity.class);
				Log.d("★★★", "BeetleKit ID = " + bk.getBeetleKitId());
				intent.putExtra(BoBBDBHelper.BEETLE_KIT_BEETLE_ID, bk.getBeetleKitId());
				startActivity(intent);
			}
		});
		
		// 対戦時使用キットに設定されている場合、設定済みアイコンを表示する。
		if (this.matchUseKitSet(kit.getBeetleKitId())) {
			Log.d("★★★★★★", "BeetleKit ID = " + bk.getBeetleKitId());
			// 設定状況
			if (kit.getType() == 1) {
				((ImageView)view.findViewById(R.id.set_ic)).setImageResource(R.drawable.sord);
			}
			if (kit.getType() == 2) {
				((ImageView)view.findViewById(R.id.set_ic)).setImageResource(R.drawable.lightning);
			}
		}

		// コンテキストを登録
		this.registerForContextMenu(view);
		
		// コンテキスト用
		this.beetleKitList.add(kit);
		this.beetleKitViewList.add(view);
		
		return view;
	}
	
//	private ArrayList<Long> beetleIdList = null;
	private long[] beetleIdList = null;
	
	/**
	 * 既に設定されている虫キットのリストを取得する。
	 */
	private void getAlreadySettingKit() {
		
		// 対戦時使用デッキに設定されている虫キットを取得する。
		long[] list1 = BattleUseKit.getAllSettingDeckID(this);
		long[] list2 = BattleUseSpecialCard.getAllSettingDeckID(this);
		
		this.beetleIdList = new long[list1.length + list2.length];
		
		int length = this.beetleIdList.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < list1.length; j++) {
				this.beetleIdList[i++] = list1[j];
			}
			for (int j = 0; j < list2.length; j++) {
				this.beetleIdList[i++] = list2[j];
			}
		}
	}
	
	/**
	 * 対戦セットと一致するのかを確認
	 * 一致する場合、trueを返却する
	 * @param id
	 * @return
	 */
	private boolean matchUseKitSet(long id) {
		boolean flag = false;
		
		int length = this.beetleIdList.length;
		for (int i = 0; i < length; i++) {
			if (id == this.beetleIdList[i]) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {
		boolean flag = false;
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch (item.getItemId()) {
		case R.id.beetlekit_list_menu_delete:
			// 削除
			
			// 削除確認のポップアップ
			
			// 削除可能な虫キットかどうかを確認
			if (this.judgePossibleDeleteKit(this.contextSelectedKit)) {
				// 削除
				this.factory.deleteBeetleKit(this.contextSelectedKit.getBeetleKitId());
				
				Toast.makeText(this, "削除されました。", Toast.LENGTH_SHORT).show();
				
				// 再表示
				this.clearListItems();
				this.createListItems();
			}
			else {
				Toast.makeText(this, "削除できません。\n" +
						"対戦キットに設定されています。", Toast.LENGTH_LONG).show();
			}

			flag = true;
			break;
		default:
			flag = super.onContextItemSelected(item);
			break;
		}
		
		return flag;
	}
	
	/**
	 * 削除可能な虫キットか判断する
	 * 可能な場合trueを返す
	 * @param kit
	 * @return
	 */
	private boolean judgePossibleDeleteKit(BeetleKit kit) {
		
		long [] beetleIdList = null;
		// Typeにより、対戦キット設定、特殊カード設定済みのカードIDを取得
		if (kit.getType() == 1) {
			beetleIdList = BattleUseKit.getAllSettingDeckID(this);
		}
		else if (kit.getType() == 2) {
			beetleIdList = BattleUseSpecialCard.getAllSettingDeckID(this);
		}
		
		// 使用中のIDと比較する
		boolean flag = true;
		long bleetleId = kit.getBeetleKitId();
		for (int i = 0; i < beetleIdList.length; i++) {
			if (bleetleId == beetleIdList[i]) {
				// 一致したらfalse
				flag = false;
				break;
			}
		}
		
		return flag;
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderIcon(R.drawable.beetleicon);
		menu.setHeaderTitle("虫キットメニュー ");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.beetlekit_list_item_menu, menu);
		
		// 長押しされたアイテムのキット情報を確定する
		int length = this.beetleKitViewList.size();
		for(int i = 0; i < length; i++) {
			if(v.equals(this.beetleKitViewList.get(i))){
				this.contextSelectedKit = this.beetleKitList.get(i);
			}
		}

	}
	
	
	
	
	
}
