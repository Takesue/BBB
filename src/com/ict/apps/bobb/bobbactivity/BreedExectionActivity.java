package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.breed.BreedManager;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ブリード（交配）画面クラス
 *
 */
public class BreedExectionActivity extends BaseActivity {

	private static Integer settingDeckNum = -1;

	// ブリード用に選択された虫キット
	private static BeetleKit selectedKit1 = null;
	private static BeetleKit selectedKit2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_breedexection);
	}	
	
	
	/**
	 * 
	 * @param view
	 */
	public void onClickBeetlekitSelection(View view) {
		
		// 画面IDを保持
		BreedExectionActivity.settingDeckNum = view.getId();
		
		// 一般虫キット選択として選択画面を呼び出し
		Intent intent = new Intent(this, BeetleKitSelectionActivity.class);
		intent.putExtra("kittype", 1);
		this.startActivityForResult(intent, 0);
		
	}
	
	/**
	 * プリード実行
	 * @param view
	 */
	public void onClickExecBreed (View view) {
		
		if (BreedExectionActivity.selectedKit1 != null && BreedExectionActivity.selectedKit2 != null) {
			BreedManager bm = new BreedManager();
			BeetleKit newBeetleKit = bm.breedBeetle(this, BreedExectionActivity.selectedKit1, BreedExectionActivity.selectedKit2);

			// 詳細画面表示
			Intent intent = new Intent(this, BeetleKitDetailActivity.class);
			
			Log.d("★★★", "BeetleKit ID = " + newBeetleKit.getBeetleKitId());
			intent.putExtra(BoBBDBHelper.BEETLE_KIT_BEETLE_ID, newBeetleKit.getBeetleKitId());
			startActivity(intent);
			
			// 設定情報のクリア
			this.clearSettingBreedKit();

		}
		else {
			Toast.makeText(this, "ブリードする虫キットが設定されていません。", Toast.LENGTH_SHORT).show();
		}
		
	}


	/**
	 * 設定済み虫キットをクリアする
	 */
	private void clearSettingBreedKit() {
		BreedExectionActivity.selectedKit1 = null;
		BreedExectionActivity.selectedKit2 = null;
		((TextView)this.findViewById(R.id.breed_exe_beetlekit1)).setText("虫キット未設定");
		((TextView)this.findViewById(R.id.breed_exe_beetlekit2)).setText("虫キット未設定");
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
				
				Log.d("★", "id:"+BreedExectionActivity.settingDeckNum);
				
				if (BreedExectionActivity.settingDeckNum == R.id.breed_exe_beetlekit1) {
					// 設定１の場合
					BreedExectionActivity.selectedKit1 = kit;
				}
				else if (BreedExectionActivity.settingDeckNum == R.id.breed_exe_beetlekit2) {
					// 設定２の場合
					BreedExectionActivity.selectedKit2 = kit;
				}
				// 画面に情報設定
				((TextView)this.findViewById(BreedExectionActivity.settingDeckNum)).setText("ID:" + beetleKitId);
			}
		}
	}


}
