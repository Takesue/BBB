package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

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
