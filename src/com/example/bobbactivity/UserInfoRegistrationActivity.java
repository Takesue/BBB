package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserInfoRegistrationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinforegistration);
	}
	
    public void returnOnClick(View v){
    	
//		Intent intent = new Intent(UserInfoRegistrationActivity.this, StartActivity.class);
//		startActivity(intent);
    	finish();
		
    }
    
	

}
