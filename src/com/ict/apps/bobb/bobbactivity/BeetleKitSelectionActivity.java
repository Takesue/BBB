package com.ict.apps.bobb.bobbactivity;

import java.util.ArrayList;

import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BeetleKitSelectionActivity extends Activity {
	
	private int kitType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_beetlekitselection);
		
		Intent intent = this.getIntent();
		this.kitType = intent.getIntExtra("kittype", -1);

		// フィルタがけ用に対戦デッキに設定されている虫キットの情報を取得しておく
		this.getAlreadySettingKit();
		
		ArrayList<BeetleKit> kitlist = null;
		BeetleKitFactory factory = new BeetleKitFactory(this);
		
		BeetleKitFactory.KitType[] types = {BeetleKitFactory.KitType.NORMAL,BeetleKitFactory.KitType.SPECIAL};
		
		// 種別１の場合 一般の虫キット
		// 種別２の場合 特殊の虫キット
		kitlist = factory.getBeetleKit(types[kitType - 1]);
		
		// 表示アイテム設定先の取得
		LinearLayout vgroup = (LinearLayout)this.findViewById(R.id.beetle_kit_selection);

		int cnt = kitlist.size();
		for (int i = 0; i < cnt; i++) {
			// 虫キット情報を表示用アイテムに設定
			vgroup.addView(this.addBeeetleKitList(kitlist.get(i)));
		}
		

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
		
		if (this.kitType == 1) {
			// 攻撃値設定
			((TextView)view.findViewById(R.id.kit_attack)).setText("攻  ：  " + kit.getAttack());
			// 守備値設定
			((TextView)view.findViewById(R.id.kit_defence)).setText("守  ：  " + kit.getDefence());
		}
		else {
			// 効果設定
			((TextView)view.findViewById(R.id.kit_attack)).setText("効果  ：  " + kit.getEffect());
			((TextView)view.findViewById(R.id.kit_defence)).setText("  ");
		}

		// ★　もし、対戦時に使用する設定にしており選択できないアイテムの場合、無効にするためリスナーを設定しない。
		// 且つ、表示でフィルターを掛ける
		if (this.matchUseKitSet(kit.getBeetleKitId())) {
			// フィルターかける
			view.findViewById(R.id.filter).setBackgroundResource(R.drawable.filter);
			((TextView)view.findViewById(R.id.deck_set_char)).setText("SET");
		}
		else {
			final BeetleKit bk = kit;
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// クリック時の処理
					Intent intent = new Intent();
					Log.d("★★★", "BeetleKit ID = " + bk.getBeetleKitId());
					intent.putExtra(BoBBDBHelper.BEETLE_KIT_BEETLE_ID, bk.getBeetleKitId());
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
		
		return view;
	}

	private long[] beetleIdList = null;
	
	/**
	 * 
	 */
	private void getAlreadySettingKit() {
		
		// Typeにより、対戦キット設定、特殊カード設定済みのカードIDを取得
		if (this.kitType == 1) {
			this.beetleIdList = BattleUseKit.getAllSettingDeckID(this);
		}
		else if (this.kitType == 2) {
			this.beetleIdList = BattleUseSpecialCard.getAllSettingDeckID(this);
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
		
		for (int i = 0; i < this.beetleIdList.length; i++) {
			if (id == this.beetleIdList[i]) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	
	

}
