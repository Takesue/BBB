package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BreedersMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breedersmenu);
	}
	
    public void newBeetleInfoOnClick(View v){
    	
		Intent intent = new Intent(BreedersMenuActivity.this, NewBeetleInfoActivity.class);
		startActivity(intent);
		
    }
    
    public void breedExectionOnClick(View v){
    	
		Intent intent = new Intent(BreedersMenuActivity.this, BreedExectionActivity.class);
		startActivity(intent);
		
    }
	
    public void battleDeckManageOnClick(View v){
    	
		Intent intent = new Intent(BreedersMenuActivity.this, BattleDeckManageActivity.class);
		startActivity(intent);
		
    }
	
    public void beetleKitListOnClick(View v){
    	
		Intent intent = new Intent(BreedersMenuActivity.this, BeetleKitListActivity.class);
		startActivity(intent);
		
    }

}
