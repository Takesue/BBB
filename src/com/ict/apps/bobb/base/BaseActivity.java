package com.ict.apps.bobb.base;

import com.ict.apps.bobb.bobbactivity.MainMenuActivity;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.bobbactivity.RuleActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Activityを継称する画面クラスの基底クラス
 * @author take
 *
 */
public abstract class BaseActivity extends Activity {
	

	// Creates the menu items
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
