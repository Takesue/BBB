package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BattleUserSelectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battleuserselect);
	}
	
    public void userOnClick1(View v){
    	
		Intent intent = new Intent(BattleUserSelectActivity.this, BattleActivity.class);
		startActivity(intent);
		
    }
	
    public void userOnClick2(View v){
    	
		Intent intent = new Intent(BattleUserSelectActivity.this, BattleActivity.class);
		startActivity(intent);
		
    }
	
    public void userOnClick3(View v){
    	
		Intent intent = new Intent(BattleUserSelectActivity.this, BattleActivity.class);
		startActivity(intent);
		
    }
    
	

}
