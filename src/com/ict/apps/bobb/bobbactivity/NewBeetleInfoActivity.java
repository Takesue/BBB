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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_newbeetleinfo);
		
		// 引数として渡された虫キットIDを取得
		Intent intent = this.getIntent();
		long barcode = intent.getLongExtra(BoBBDBHelper.READ_BARCODE, 0l);
		
		// 新規カード取得（バーコード番号からカード生成）  DBにカード情報を登録
		BreedManager bm = new BreedManager();
		BeetleKit kit = bm.generateCardStatusFromBarcode(this, barcode);
		if (kit == null) {
			// 生成失敗
			finish();
			return;
		}
		
		// 生成時効果音再生
		MediaPlayer mp = MediaPlayer.create(this, R.raw.waoh);
		mp.start();

		LinearLayout vgroup = (LinearLayout)this.findViewById(R.id.newBeetleKit);
		vgroup.setGravity(Gravity.CENTER_HORIZONTAL);
		View view = this.getLayoutInflater().inflate(R.layout.card_detailview, vgroup);
		
		// 名前設定
		((TextView)view.findViewById(R.id.carddetail_name)).setText(kit.getName());
		// 説明設定
		((TextView)view.findViewById(R.id.carddetail_atk)).setText("攻：" + kit.getAttack());
		// 説明設定
		((TextView)view.findViewById(R.id.carddetail_def)).setText("守：" + kit.getDefence());
		// 説明設定
		((TextView)view.findViewById(R.id.carddetail_intoro)).setText("説明：" + kit.getIntroduction());
		// 画像設定
		((ImageView)view.findViewById(R.id.carddetail_icon)).setImageResource(kit.getImageResourceId(this));
		// 画像設定
		((ImageView)view.findViewById(R.id.carddetail_attrribute)).setImageResource(R.drawable.wind);
		
		// 取得ボタン生成
		final BreedManager manager = bm;
		final BeetleKit bk = kit;
		final Context context = this;
		
		Button getButton = new Button(this);
		getButton.setText("取得");
		getButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 虫キット情報テーブルに登録
				manager.registBeetleKit(context, bk);
				// バーコード履歴に登録
				manager.insertBarcodeToDB(context, bk.getBarcode_id());
				
				Toast.makeText(context, "虫キットを取得しました。", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		vgroup.addView(getButton);

		// キャンセルボタン生成
		Button cancelButton = new Button(this);
		cancelButton.setText("キャンセル");
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		vgroup.addView(cancelButton);

	}
	
    public void getOnClick(View v){
//		画面遷移はなさそうなので、保留。
    }
	
    public void returnOnClick(View v){
    	
		Intent intent = new Intent(NewBeetleInfoActivity.this, BreedersMenuActivity.class);
		startActivity(intent);
		
    }
    
	

}
