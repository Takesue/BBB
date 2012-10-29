package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BeetleKitListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beetlekitlist);
	}
	
    public void kitOnClick1(View v){
    	
		Intent intent = new Intent(BeetleKitListActivity.this, BeetleKitDetailActivity.class);
		startActivity(intent);
		
    }
	
    public void kitOnClick2(View v){
    	
		Intent intent = new Intent(BeetleKitListActivity.this, BeetleKitDetailActivity.class);
		startActivity(intent);
		
    }
	
    public void kitOnClick3(View v){
    	
		Intent intent = new Intent(BeetleKitListActivity.this, BeetleKitDetailActivity.class);
		startActivity(intent);
		
    }
	
}
