package com.ict.apps.bobb.bobbactivity;

import java.util.ArrayList;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_beetlekitselection);
		
		Intent intent = this.getIntent();
		int kitType = intent.getIntExtra("kittype", -1);

		ArrayList<BeetleKit> kitlist = null;
		BeetleKitFactory factory = new BeetleKitFactory(this);
		if (kitType == 1) {
			// 種別１の場合 一般の虫キット
			kitlist = factory.getBeetleKit(BeetleKitFactory.KitType.NORMAL);
		}
		else {
			// 種別１の場合 特殊の虫キット
			kitlist = factory.getBeetleKit(BeetleKitFactory.KitType.SPECIAL);
		}
		
		
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
		// 攻撃値設定
		((TextView)view.findViewById(R.id.kit_attack)).setText("攻  ：  " + kit.getAttack());
		// 守備値設定
		((TextView)view.findViewById(R.id.kit_defence)).setText("守  ：  " + kit.getDefence());
		
		// リスナーの設定
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
		
		return view;
	}


}
