package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class StartActivity extends Activity {

//一回目のインなのかの判断で使用しているだけですので、後で正しく組み直してください。
	public Boolean count = false;
//ここまで
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start, menu);
        return true;
    } 
    
    public void startOnClick(View v){
    	
    	Intent intent;
    	if(this.count == true){
    		intent = new Intent(StartActivity.this, MainMenuActivity.class);
    	}else{
    		intent = new Intent(StartActivity.this, UserInfoRegistrationActivity.class);
    		this.count = true;
    	}

//		Intent intent = new Intent(StartActivity.this, MainMenuActivity.class);
		startActivity(intent);
    }
    
    public void ruleOnClick(View v){
    	
		Intent intent = new Intent(StartActivity.this, RuleActivity.class);
		startActivity(intent);
		
    }
    
}
