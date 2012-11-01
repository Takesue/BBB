package com.ict.apps.bobb.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BattleActivity extends Activity {

	Integer num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        this.battleCardsSet();
	}
	
    public void finishOnClick(View v){
    	
		Intent intent = new Intent(BattleActivity.this, MainMenuActivity.class);
		startActivity(intent);
		
    }
    
	public void battleCardsSet(){
		BattleCardsSet bcs = new BattleCardsSet(this);
		setContentView(bcs);
	}

}
