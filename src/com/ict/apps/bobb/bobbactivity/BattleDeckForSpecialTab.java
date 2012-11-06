package com.ict.apps.bobb.bobbactivity;

import java.util.ArrayList;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BattleDeckForSpecialTab extends BaseActivity {

	private static Integer settingDeckNum = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tab_battledeck_special);
		ArrayList<BeetleKit> kitlist = new ArrayList<BeetleKit>();
		// 戦闘時使用キットクラスの使用例
		kitlist.add(BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD1));
		kitlist.add(BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD2));
		kitlist.add(BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD3));

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
		
		TypedArray titles = getResources().obtainTypedArray(R.array.spe_deck_title);
		TypedArray attacks = getResources().obtainTypedArray(R.array.spe_deck_attack);
		TypedArray defences = getResources().obtainTypedArray(R.array.spe_deck_defence);
		TypedArray ic = getResources().obtainTypedArray(R.array.spe_deck_ic);
		
		// 名前設定
		((TextView)this.findViewById(titles.getResourceId(i, -1))).setText(kit.getName());
		// アイコン画像設定
		ImageView image = (ImageView)this.findViewById(ic.getResourceId(i, -1));
		image.setImageResource(kit.getImageResourceId(this));
		// 攻撃値設定
		((TextView)this.findViewById(attacks.getResourceId(i, -1))).setText("効果：" + kit.getEffect());
		// 守備値設定
		((TextView)this.findViewById(defences.getResourceId(i, -1))).setText(" ");
		
	}
	
	/**
	 * デッキボタンクリック時
	 * @param view
	 */
	public void onClickDeckButton(View view) {
		
		TypedArray buttons = getResources().obtainTypedArray(R.array.spe_deck_buttons);
		
		int id = view.getId();
		int length = buttons.length();
		for (int i = 0; i < length; i++) {
			if (id == buttons.getResourceId(i, -1)) {
				BattleDeckForSpecialTab.settingDeckNum = i;
			}
		}
		
		// クリック時の処理
		Intent intent = new Intent(this , BeetleKitSelectionActivity.class);
		intent.putExtra("kittype", 2);
		startActivityForResult(intent, 0);
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
				
				BattleUseSpecialCard.CardNum[] cardNumList = {
						BattleUseSpecialCard.CardNum.CARD1,
						BattleUseSpecialCard.CardNum.CARD2,
						BattleUseSpecialCard.CardNum.CARD3
				};

				// 設定情報を保存
				BattleUseSpecialCard.setUseKit(this, cardNumList[BattleDeckForSpecialTab.settingDeckNum], kit);
				
				Log.d("★", "id:"+BattleDeckForSpecialTab.settingDeckNum);
				
				// 画面に設定
				this.addBeeetleKitList(kit, BattleDeckForSpecialTab.settingDeckNum);
			}
		}
	}



}
