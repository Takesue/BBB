package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class BreedersMenuActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_breedersmenu);

	}

	/**
	 * 採取ボタン選択時
	 * @param v
	 */
	public void newBeetleInfoOnClick(View v) {
		// バーコード機能呼び出し
		this.callBarcode();

	}

	/**
	 * ブリードボタン選択時
	 * @param v
	 */
	public void breedExectionOnClick(View v) {

		Intent intent = new Intent(BreedersMenuActivity.this, BreedExectionActivity.class);
		this.startActivity(intent);

	}

	/**
	 * 対戦デッキボタン選択時
	 * @param v
	 */
	public void battleDeckManageOnClick(View v) {

		Intent intent = new Intent(BreedersMenuActivity.this, BattleDeckManageActivity.class);
		this.startActivity(intent);

	}

	/**
	 * 虫キット一覧ボタン選択時
	 * @param v
	 */
	public void beetleKitListOnClick(View v) {

		Intent intent = new Intent(BreedersMenuActivity.this, BeetleKitListActivity.class);
		this.startActivity(intent);

	}

	
	
	/**
	 * バーコードリーダを呼びます
	 */
	public void callBarcode() {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "ONE_D_MODE");
		
		try {
			this.startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException ex) { 
			
			new AlertDialog.Builder(BreedersMenuActivity.this)
					.setTitle("QR code Scaner not found.")
					.setMessage("Please install QR code Scanner")
					.setPositiveButton("Download",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// "Download を選択するとダウンロード先に遷移する
									Uri url = Uri.parse("market://details?id=com.google.zxing.client.android");

									Intent intent = new Intent(Intent.ACTION_VIEW, url);
									startActivity(intent);
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// "Cancel"を選択すると終わる
									dialog.dismiss();
								}
							}).show();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				String barcode = data.getStringExtra("SCAN_RESULT");
				
//				this.playEffect(R.raw.breed_create1);
				
				// バーコード情報を渡して取得結果画面へ遷移する。
				Intent intent = new Intent(BreedersMenuActivity.this, NewBeetleInfoActivity.class);
				intent.putExtra(BoBBDBHelper.READ_BARCODE, Long.valueOf(barcode));
				this.startActivity(intent);
				
			}
		}
	}
	

}
