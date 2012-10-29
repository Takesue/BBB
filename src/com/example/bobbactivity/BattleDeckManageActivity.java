package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BattleDeckManageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battledeckmanage);
	}
	
    public void deckOnClick1(View v){
    	
		Intent intent = new Intent(BattleDeckManageActivity.this, BeetleKitSelectionActivity.class);
		startActivity(intent);
		
    }
	
    public void deckOnClick2(View v){
    	
		Intent intent = new Intent(BattleDeckManageActivity.this, BeetleKitSelectionActivity.class);
		startActivity(intent);
		
    }
	
    public void deckOnClick3(View v){
    	
		Intent intent = new Intent(BattleDeckManageActivity.this, BeetleKitSelectionActivity.class);
		startActivity(intent);
		
    }
    
	

}
