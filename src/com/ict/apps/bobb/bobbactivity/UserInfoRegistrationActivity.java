package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.online.OnlineUtil;
import com.ict.apps.bobb.online.OnlineOneTimeTask;
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQueryUserRegister;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfoRegistrationActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_userinforegistration);

		// ブロードキャストレシーバの登録
		this.registerReceiver(this.mHandleQeryCompReceiver, new IntentFilter(OnlineUtil.QERY_COMPLETE_ACTION));

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// ブロードキャストレシーバの解除
		this.unregisterReceiver(mHandleQeryCompReceiver);

	}



	public void registOnClick(View v) {
		this.registerUserInfo();


	}
	
	public void finishActivity() {
		this.setResult(RESULT_OK, new Intent());
		this.finish();
	}
	
	
	/**
	 * ユーザ情報を登録する
	 */
	private void registerUserInfo() {

		// 入力されたユーザ名のチェック
		final String name = ((EditText)this.findViewById(R.id.registUserName)).getText().toString();
		if ((name == null) || ("".equals(name))){
			Toast.makeText(this, "入力された名前が不正です。再入力してください。", Toast.LENGTH_LONG).show();
			return ;
		}
		
		
		StatusInfo.setUserName(this, name);
		OnlineQueryUserRegister query = new OnlineQueryUserRegister();
		query.setUserName(StatusInfo.getUserName(this));
		new OnlinePoolingTask(this).execute(query);

	}
	
	// ブロードキャストメッセージ
	// バックグラウンドのPoolingが終了したことを受ける
	private final BroadcastReceiver mHandleQeryCompReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 画面終了
			finishActivity();
		}
	};

}
