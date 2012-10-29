package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BreedExectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breedexection);
	}
	
    public void cardOnClick1(View v){
    	
		Intent intent = new Intent(BreedExectionActivity.this, BeetleKitSelectionActivity.class);
		startActivity(intent);
		
    }
	
    public void cardOnClick2(View v){
    	
		Intent intent = new Intent(BreedExectionActivity.this, BeetleKitSelectionActivity.class);
		startActivity(intent);
		
    }
	
    public void crossbredOnClick(View v){
    	
		Intent intent = new Intent(BreedExectionActivity.this, NewBeetleInfoActivity.class);
		startActivity(intent);
		
    }
    
	

}
