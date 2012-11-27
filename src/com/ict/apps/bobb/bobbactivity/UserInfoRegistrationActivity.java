package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.online.OnlineOneTimeTask;
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQueryUserRegister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfoRegistrationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinforegistration);
	}
	
	public void registOnClick(View v) {
		this.registerUserInfo();

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

		// レスポンスで得られるユーザIDはブロードキャストでUserIdを設定する
		// UserIdが設定されるまで、ループで待つ
		while (true) {
			try {
				// ユーザIDが格納されたか確認
				if (!"".equals(StatusInfo.getUserId(this))) {
					// ユーザIDが格納されたらループを脱出
					break;
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
