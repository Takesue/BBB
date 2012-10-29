package com.example.bobbactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewBeetleInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbeetleinfo);
	}
	
    public void getOnClick(View v){
//		画面遷移はなさそうなので、保留。
    }
	
    public void returnOnClick(View v){
    	
		Intent intent = new Intent(NewBeetleInfoActivity.this, BreedersMenuActivity.class);
		startActivity(intent);
		
    }
    
	

}
