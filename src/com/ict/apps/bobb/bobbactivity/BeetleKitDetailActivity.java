package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 虫キット詳細表示画面
 *
 */
public class BeetleKitDetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_beetlekitdetail);
		
		// 引数として渡された虫キットIDを取得
		Intent intent = this.getIntent();
		long beetleid = intent.getLongExtra(BoBBDBHelper.BEETLE_KIT_BEETLE_ID, 0l);
		
		// 虫キットIDをキーに虫キット情報を取得
		BeetleKitFactory factory = new BeetleKitFactory(this);
		BeetleKit kit = factory.getBeetleKit(beetleid);
		
		LinearLayout vgroup = (LinearLayout)this.findViewById(R.id.beetleKitDetail);
		vgroup.setGravity(Gravity.CENTER_HORIZONTAL);
		View view = this.getLayoutInflater().inflate(R.layout.beetlekitcard_detailview, vgroup);
		
		// 名前設定
		((TextView)view.findViewById(R.id.beetlekit_carddetail_name)).setText(kit.getName());
		
		if (kit.getType() == 1) {
			
			((TextView)view.findViewById(R.id.beetlekit_carddetail_type)).setText("モンスターカード");
			// 説明設定
			((TextView)view.findViewById(R.id.beetlekit_carddetail_atk)).setText("攻：" + kit.getAttack());
			// 説明設定
			((TextView)view.findViewById(R.id.beetlekit_carddetail_def)).setText("守：" + kit.getDefense());
		}
		else if (kit.getType() == 2) {
			((TextView)view.findViewById(R.id.beetlekit_carddetail_type)).setText("効果カード");
			// 説明設定
			((TextView)view.findViewById(R.id.beetlekit_carddetail_atk)).setText("効果：" + kit.getEffect());
			// 説明設定
			((TextView)view.findViewById(R.id.beetlekit_carddetail_def)).setText(" ");
		}
		// 説明設定
		((TextView)view.findViewById(R.id.beetlekit_carddetail_intoro)).setText("  " + kit.getIntroduction());
		// 画像設定
		((ImageView)view.findViewById(R.id.beetlekit_carddetail_icon)).setImageResource(kit.getImageResourceId(this));


//		// 戻るボタン生成
//		Button button = new Button(this);
//		button.setText("戻る");
//		button.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//		vgroup.addView(button);

	}

	public void onClickButton(View v) {

		this.finish();
	}

}
