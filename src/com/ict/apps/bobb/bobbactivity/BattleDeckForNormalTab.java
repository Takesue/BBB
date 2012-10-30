package com.ict.apps.bobb.bobbactivity;

import java.util.ArrayList;

import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BattleDeckForNormalTab extends Activity {

	private static Integer settingDeckNum = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tab_battledeck_normal);
		
		ArrayList<BeetleKit> kitlist = new ArrayList<BeetleKit>();
		// 戦闘時使用キットクラスの使用例
		kitlist.add(BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK1));
		kitlist.add(BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK2));
		kitlist.add(BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK3));
		kitlist.add(BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK4));
		kitlist.add(BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK5));

		// 表示アイテム設定先の取得
		LinearLayout vgroup = (LinearLayout)this.findViewById(R.id.battleUseDeckList);

		int cnt = kitlist.size();
		for (int i = 0; i < cnt; i++) {
			// 虫キット情報を表示用アイテムに設定
			this.addBeeetleKitList(kitlist.get(i), i);
		}

	}
	
	/**
	 * アイコン表示付きリストに虫キット情報追加
	 * @param mInflater
	 * @param vgroup
	 * @param kit
	 */
	private void addBeeetleKitList(BeetleKit kit, Integer i) {
		
		int[] viewidList = {R.id.deck1,
				R.id.deck2,
				R.id.deck3,
				R.id.deck4,
				R.id.deck5};

		TextView view = (TextView)this.findViewById(viewidList[i]);
		view.setText("id:" + kit.getBeetleKitId());
		
//		
//		// 名前設定
//		((TextView)view.findViewById(R.id.kit_name)).setText(kit.getName());
//		// アイコン画像設定
//		ImageView image = (ImageView)view.findViewById(R.id.kiticon);
//		image.setImageResource(kit.getImageResourceId(this));
//		// 攻撃値設定
//		((TextView)view.findViewById(R.id.kit_attack)).setText("攻  ：  " + kit.getAttack());
//		// 守備値設定
//		((TextView)view.findViewById(R.id.kit_defence)).setText("守  ：  " + kit.getDefence());
		
		// リスナーの設定
		final Integer deckNum = i;
		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BattleDeckForNormalTab.settingDeckNum = deckNum;
				Log.d("★★", "id::"+BattleDeckForNormalTab.settingDeckNum);
				// クリック時の処理
				Intent intent = new Intent(BattleDeckForNormalTab.this, BeetleKitSelectionActivity.class);
				intent.putExtra("kittype", 1);
				startActivityForResult(intent, 0);
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				long beetleKitId = data.getLongExtra(BoBBDBHelper.BEETLE_KIT_BEETLE_ID, 0);
				
				// IDをキーに虫キットインスタンスを取得
				BeetleKitFactory factory = new BeetleKitFactory(this);
				BeetleKit kit = factory.getBeetleKit(beetleKitId);
				
				BattleUseKit.DeckNum[] deckNumList = {
						BattleUseKit.DeckNum.DECK1,
						BattleUseKit.DeckNum.DECK2,
						BattleUseKit.DeckNum.DECK3,
						BattleUseKit.DeckNum.DECK4,
						BattleUseKit.DeckNum.DECK5
				};

				// 設定情報を保存
				BattleUseKit.setUseKit(this, deckNumList[BattleDeckForNormalTab.settingDeckNum], kit);
				
				Log.d("★", "id:"+BattleDeckForNormalTab.settingDeckNum);
				
				// 画面に設定
				this.addBeeetleKitList(kit, BattleDeckForNormalTab.settingDeckNum);
			}
		}
	}



}
