package com.ict.apps.bobb.bobbactivity;

import java.util.ArrayList;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQueryOnlineUserList;
import com.ict.apps.bobb.online.OnlineQueryUserRegister;
import com.ict.apps.bobb.online.OnlineUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainMenuActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_mainmenu);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

    public void breedersMenuOnClick(View v){
    	
		Intent aintent = new Intent(MainMenuActivity.this, BreedersMenuActivity.class);
		startActivity(aintent);
		
    }
    
    public void battleCpuOnClick(View v){
    	
		Intent bintent = new Intent(MainMenuActivity.this, BattleUserSelectActivity.class);
		startActivity(bintent);
		
    }
	
    private OnlineQueryOnlineUserList query = null;
    public void battleHumanOnClick(View v){
    	
    	// 通信して対戦相手のリストを取得要求を出す
    	this.query = new OnlineQueryOnlineUserList();
    	this.query.setUserId(StatusInfo.getUserId(this));
    	this.query.setLevel(StatusInfo.getLevel(this));
		new OnlinePoolingTask(this).execute(this.query);
    	
//		Intent cintent = new Intent(MainMenuActivity.this, BattleUserSelectActivity.class);
//		startActivity(cintent);
		
    }

    public ArrayList<Integer> index = new ArrayList<Integer>();
	/**
	 * ポップアップでユーザリストを表示する
	 */
	public void viewPopupUserLis() {
		
		int battleUserNum = this.query.getResponseRecordCount();
		
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<String> ids = new ArrayList<String>();
		String userId = StatusInfo.getUserId(this);
		for (int i = 0; i < battleUserNum; i++) {
			
			// 自端末のIDは除外
			if (userId.equals(this.query.getResponseData(i, "user_id"))) {
				continue;
			}
			
			String reqMark = "";
			if (!"0".equals(this.query.getResponseData(i, "battle_id"))) {
				// 対戦要求を出しているユーザに星マークを付けて表示
				reqMark = "★";
			}
			
			int idIndex = ids.indexOf(this.query.getResponseData(i, "user_id"));
			if (idIndex != -1) {
				// 既出のIDは追加しない
				continue;
			}
			
			// 既出で無いユーザIDの場合リストに追加
			items.add("[User] " + this.query.getResponseData(i, "user_name")
						+ "   Lv:" + this.query.getResponseData(i, "user_level") 
						+ " " + reqMark);
			this.index.add(i);
			ids.add(this.query.getResponseData(i, "user_id"));
		}
		
		String[] str_items = new String[items.size()];
		str_items = items.toArray(str_items);
		
		
		if (items.size() == 0) {
			// 検索結果対象が0件の場合
			// 対戦相手がいない
			Toast.makeText(this, "オンラインに対戦相手が見当たりません", Toast.LENGTH_LONG);
			return;
		}

		new AlertDialog.Builder(this).setIcon(R.drawable.beetleicon)
				.setTitle("対戦相手")
				.setItems(str_items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						Log.d("MainMenuActivity", "select num:" + which);
						//
						Intent cintent = new Intent(MainMenuActivity.this,
								BattleActivity.class);
						cintent.putExtra("user_mode", "online");
						cintent.putExtra("user_id", query.getResponseData(index.get(which), "user_id"));
						cintent.putExtra("user_name", query.getResponseData(index.get(which), "user_name"));
						cintent.putExtra("registration_id", query.getResponseData(index.get(which), "transaction_id"));
						cintent.putExtra("battle_id", query.getResponseData(index.get(which), "battle_id"));
						startActivity(cintent);
						
						// インデックスをクリア
						index.clear();
					}
				}).show();

	}

}
