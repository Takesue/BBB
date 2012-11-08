package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.breed.BreedManager;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 新しい虫キット表示画面クラス
 *
 */
public class NewBeetleInfoActivity extends BaseActivity {

	private BreedManager bm = null;
	private BeetleKit kit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_newbeetleinfo);
		
		// 引数として渡された虫キットIDを取得
		Intent intent = this.getIntent();
		long barcode = intent.getLongExtra(BoBBDBHelper.READ_BARCODE, 0l);
		
		// 新規カード取得（バーコード番号からカード生成）  DBにカード情報を登録
		bm = new BreedManager();
		kit = bm.generateCardStatusFromBarcode(this, barcode);
		if (kit == null) {
			// 生成失敗
			finish();
			return;
		}
		else {
			// 生成効果音再生
			this.playEffect(R.raw.breed_create1);
		}
		
		LinearLayout vgroup = (LinearLayout)this.findViewById(R.id.newBeetleKit);
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
		
	}
	
	public void onClickGet(View v) {
		// 虫キット情報テーブルに登録
		this.bm.registBeetleKit(this, this.kit);
		// バーコード履歴に登録
		this.bm.insertBarcodeToDB(this, this.kit.getBarcode_id());
		
		Toast.makeText(this, "虫キットを取得しました。", Toast.LENGTH_SHORT).show();

		this.finish();
	}

	public void onClickCancel(View v) {

		this.finish();
	}

}
