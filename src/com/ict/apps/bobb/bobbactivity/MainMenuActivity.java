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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainMenuActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        

	}
	
    public void breedersMenuOnClick(View v){
    	
		Intent aintent = new Intent(MainMenuActivity.this, BreedersMenuActivity.class);
		startActivity(aintent);
		
    }
    
    public void battleCpuOnClick(View v){
    	
		Intent bintent = new Intent(MainMenuActivity.this, BattleUserSelectActivity.class);
		startActivity(bintent);
		
    }
	
    public void battleHumanOnClick(View v){
    	
		Intent cintent = new Intent(MainMenuActivity.this, BattleUserSelectActivity.class);
		startActivity(cintent);
		
    }
	


}
