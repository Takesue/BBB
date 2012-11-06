package com.ict.apps.bobb.base;

import com.ict.apps.bobb.bobbactivity.MainMenuActivity;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.bobbactivity.RuleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Activityを継称する画面クラスの基底クラス
 *
 */
public abstract class BaseActivity extends Activity {
	

	// BGM管理インスタンス
	private static BgmManager bgm = null;

	// 効果音管理インスタンス
	private static SoundEffectManager effect = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 効果音をロードしておく
		this.loadEffect();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		this.startBgm();
	}

	/**
	 * BGM再生
	 */
	protected void startBgm() {
		
		if (BaseActivity.bgm == null) {
			BaseActivity.bgm = BgmManager.get(this);
		}

		BaseActivity.bgm.play(R.raw.breed_bgm);
	}
	
	/**
	 * BGM停止
	 */
	protected void stopBgm() {
		if (BaseActivity.bgm != null) {
			BaseActivity.bgm.stop();
		}
	}
	
	/**
	 * 効果音リソースをあらかじめロードしておく
	 */
	private void loadEffect() {
		
		// 効果音をロードしておく
		BaseActivity.effect = SoundEffectManager.getInstance(this);
		BaseActivity.effect.loadEffect(R.raw.bane);
		
	}
	
	/**
	 * 指定したリソースIDの効果音を再生する
	 * @param resId
	 */
	protected void playEffect(int resId) {
		BaseActivity.effect.play(resId);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
//		Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
		
		if (BaseActivity.bgm.matchPlayContext(this)) {
			// 回収処理
			this.stopBgm();
			BgmManager.release();
			BaseActivity.bgm = null;
//			Toast.makeText(getApplicationContext(), "releace BGM instance", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		
		if (BaseActivity.bgm != null) {
			this.stopBgm();
//			BgmManager.release();
//			BaseActivity.bgm = null;
		}
//		Toast.makeText(getApplicationContext(), "onUserLeaveHint", Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bobb_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.menu_top:
			// TOPへ戻る
			// いままでの画面スタックは削除してトップ画面に戻る
			Intent intent = new Intent(this, MainMenuActivity.class);  
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
				Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.startActivity(intent); 

			break;
		case R.id.menu_rule:
			// ルール説明
			Toast.makeText(this, "ruleだよ", Toast.LENGTH_SHORT).show();
			this.callRuleView();
			break;
		 }
		
		return true;
	}
	
	/**
	 * 説明画面を呼び出す
	 */
	public void callRuleView() {
		Intent intent = new Intent(this, RuleActivity.class);
		this.startActivity(intent); 
	}
	
}
