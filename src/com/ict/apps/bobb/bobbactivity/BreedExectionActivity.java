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
import android.widget.ImageView;
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
		
		this.clearSettingBreedKit();
	}	
	
	
	/**
	 * ブリード設定画面をクリックした場合、選択画面へ遷移
	 * @param view
	 */
	public void onClickBeetlekitSelection(View view) {
		
		// 設定済みの虫キット情報を虫キット選択画面へ渡すため
		long othorKitId1 = -1;
		long othorKitId2 = -1;
		
		if (BreedExectionActivity.selectedKit1 != null) {
			othorKitId1 = BreedExectionActivity.selectedKit1.getBeetleKitId();
		}
		if (BreedExectionActivity.selectedKit2 != null) {
			othorKitId2 = BreedExectionActivity.selectedKit2.getBeetleKitId();
		}
		
		// 画面IDを保持
		BreedExectionActivity.settingDeckNum = view.getId();
		
		// 一般虫キット選択として選択画面を呼び出し
		Intent intent = new Intent(this, BeetleKitSelectionActivity.class);
		intent.putExtra("kittype", 1);
		intent.putExtra("alreadySetKitId1", othorKitId1);
		intent.putExtra("alreadySetKitId2", othorKitId2);
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
			Toast.makeText(this, "虫キットが設定されていません。", Toast.LENGTH_SHORT).show();
		}
		
	}


	/**
	 * 設定済み虫キットをクリアする
	 */
	private void clearSettingBreedKit() {
		BreedExectionActivity.selectedKit1 = null;
		BreedExectionActivity.selectedKit2 = null;
		((ImageView)this.findViewById(R.id.breed_exe_beetlekitImage1)).setImageResource(R.drawable.breed_kit_unset);
		((ImageView)this.findViewById(R.id.breed_exe_beetlekitImage2)).setImageResource(R.drawable.breed_kit_unset);
		((TextView)this.findViewById(R.id.breedkit_name_1)).setText("虫キットを");
		((TextView)this.findViewById(R.id.breedkit_attack_1)).setText("設定して");
		((TextView)this.findViewById(R.id.breedkit_defence_1)).setText("ください");
		((TextView)this.findViewById(R.id.breedkit_name_2)).setText("虫キットを");
		((TextView)this.findViewById(R.id.breedkit_attack_2)).setText("設定して");
		((TextView)this.findViewById(R.id.breedkit_defence_2)).setText("ください");
		
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
					((ImageView)this.findViewById(R.id.breed_exe_beetlekitImage1)).setImageResource(kit.getImageResourceId(this));
					((TextView)this.findViewById(R.id.breedkit_name_1)).setText(kit.getName());
					((TextView)this.findViewById(R.id.breedkit_attack_1)).setText("攻：" + kit.getAttack());
					((TextView)this.findViewById(R.id.breedkit_defence_1)).setText("守：" + kit.getDefence());
					
				}
				else if (BreedExectionActivity.settingDeckNum == R.id.breed_exe_beetlekit2) {
					// 設定２の場合
					BreedExectionActivity.selectedKit2 = kit;
					((ImageView)this.findViewById(R.id.breed_exe_beetlekitImage2)).setImageResource(kit.getImageResourceId(this));
					((TextView)this.findViewById(R.id.breedkit_name_2)).setText(kit.getName());
					((TextView)this.findViewById(R.id.breedkit_attack_2)).setText("攻：" + kit.getAttack());
					((TextView)this.findViewById(R.id.breedkit_defence_2)).setText("守：" + kit.getDefence());
				}
				
			}
		}
	}


}
