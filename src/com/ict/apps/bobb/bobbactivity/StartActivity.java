package com.ict.apps.bobb.bobbactivity;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gcm.GCMRegistrar;
import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.battle.player.CPU01;
import com.ict.apps.bobb.battle.player.CPU02;
import com.ict.apps.bobb.battle.player.CPU03;
import com.ict.apps.bobb.battle.player.CPU04;
import com.ict.apps.bobb.battle.player.OnlinePlayer;
import com.ict.apps.bobb.battle.player.Player;
import com.ict.apps.bobb.battle.player.PlayerListAdapter;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;
import com.ict.apps.bobb.db.BoBBDBHelper;
import com.ict.apps.bobb.online.OnlineQuery;
import com.ict.apps.bobb.online.OnlineQueryOnlineUserList;
import com.ict.apps.bobb.online.OnlineResponseListener;
import com.ict.apps.bobb.online.OnlineUtil;
import com.ict.apps.bobb.online.OnlineConnection;
import com.ict.apps.bobb.online.OnlineOneTimeTask;
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQueryAccessLog;
import com.ict.apps.bobb.online.OnlineQueryUserRegister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class StartActivity extends BaseActivity {

//一回目のインなのかの判断で使用しているだけですので、後で正しく組み直してください。
	public Boolean count = false;
//ここまで
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);

        // 初回だけ呼び出す
		if (StatusInfo.getUserId(this).equals("")) {
			
	    	Intent intent;
    		intent = new Intent(this, UserInfoRegistrationActivity.class);
			this.startActivityForResult(intent, 0);
			
		}
		else {
			this.init();
			// カード生成
			// テスト用に呼び出している。
//			UserInfoRegistrationActivity.createDefauleCards(this);
		}
    }

    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				this.init();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// ブロードキャストの登録解除
		OnlineUtil.unregisterDevice(this);

	}

//	public void startOnClick(View v){
//    	
//    	Intent intent;
////    	if(this.count == true){
//    		intent = new Intent(StartActivity.this, MainMenuActivity.class);
////    	}else{
////    		intent = new Intent(StartActivity.this, UserInfoRegistrationActivity.class);
////    		this.count = true;
////    	}
//
////		Intent intent = new Intent(StartActivity.this, MainMenuActivity.class);
//		startActivity(intent);
//    }
//    
//    public void ruleOnClick(View v){
//    	
//		Intent intent = new Intent(StartActivity.this, RuleActivity.class);
//		startActivity(intent);
//		
//	}

    
    
	/**
	 * ブリーダーメニューボタンクリック時
	 * @param v
	 */
	public void breedersMenuOnClick(View v) {

		Intent aintent = new Intent(this, BreedersMenuActivity.class);
		startActivity(aintent);

	}

	/**
	 * CPU対戦ボタンクリック時
	 * @param v
	 */
	public void battleCpuOnClick(View v) {

		final Player[] userList = {
			new CPU01(this),
			new CPU02(this),
			new CPU03(this),
			new CPU04(this)
		};

		this.viewPopupPlayerLis(userList);
	}
	
	private OnlineQueryOnlineUserList query = null;

	/**
	 * オンライン対戦ボタンクリック時
	 * @param v
	 */
	public void battleHumanOnClick(View v) {

		// 通信して対戦相手のリストを取得要求を出す
		this.query = new OnlineQueryOnlineUserList();
		this.query.setUserId(StatusInfo.getUserId(this));
		this.query.setLevel(StatusInfo.getLevel(this));
		this.query.setListner(new OnlineResponseListener() {
			@Override
			public void response(Context context, OnlineQuery query, Integer result) {
				viewPopupUserLis();
			}
		});
		new OnlinePoolingTask(this).execute(this.query);
	}


	private ArrayList<Integer> index = new ArrayList<Integer>();

	/**
	 * ポップアップでオンラインユーザリストを表示する
	 */
	public void viewPopupUserLis() {
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		ArrayList<String> ids = new ArrayList<String>();
		String userId = StatusInfo.getUserId(this);
		
		int battleUserNum = this.query.getResponseRecordCount();
		for (int i = 0; i < battleUserNum; i++) {
			
			// 自端末のIDは除外
			if (userId.equals(this.query.getResponseData(i, "user_id"))) {
				continue;
			}
			

			int idIndex = ids.indexOf(this.query.getResponseData(i, "user_id"));
			if (idIndex != -1) {
				// 既出のIDは追加しない
				continue;
			}
			
			// 既出で無いユーザIDの場合リストに追加
			Player p = new OnlinePlayer(this);
			p.setName(this.query.getResponseData(i, "user_name"));
			p.setLevel(Integer.parseInt(this.query.getResponseData(i, "user_level")));
			p.setRequest(this.query.getResponseData(i, "battle_id"));
			
			playerList.add(p);
			index.add(i);
			ids.add(this.query.getResponseData(i, "user_id"));
		}
		
		Player[] items = new Player[playerList.size()];
		items = playerList.toArray(items);
		
		
		if (items.length == 0) {
			// 検索結果対象が0件の場合
			// 対戦相手がいない
			Toast.makeText(this, "オンラインに対戦相手が見当たりません", Toast.LENGTH_LONG).show();
			return;
		}
		
		// ポップアップリスト表示
		this.viewPopupPlayerLis(items);
		
	}
	
	
	
	/**
	 * ポップアップでユーザリストを表示する
	 */
	public void viewPopupPlayerLis(final Player[] userList) {
		
		//コンテキストからインフレータを取得
		LayoutInflater inflater = LayoutInflater.from(this.getBaseContext());

		//レイアウトXMLからビュー(レイアウト)をインフレート
		final View playerList = inflater.inflate(R.layout.player_list, null);
		
		PlayerListAdapter userListAdapter = new PlayerListAdapter(this, userList);
		
		
		final AlertDialog ad = new AlertDialog.Builder(this)
		.setView(playerList)
		.show();

		((TextView) playerList.findViewById(R.id.popupTitle)).setText("対戦CPU選択");
		((TextView) playerList.findViewById(R.id.popupCancel)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ×クリックでポップアップ閉じる
				ad.cancel();
			}
		});

		ListView listView = (ListView) playerList.findViewById(R.id.playerList);
		
		// アダプターを設定します
		listView.setAdapter(userListAdapter);
		
		// リストビューのアイテムがクリックされた時に呼び出されるコールバックリスナーを登録します
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// クリックされたアイテムを取得します
				Intent cintent = new Intent(StartActivity.this, BattleActivity.class);
				
				if ( userList[position] instanceof OnlinePlayer ) {
					cintent.putExtra("user_mode", "online");
					cintent.putExtra("user_id", query.getResponseData(index.get(position), "user_id"));
					cintent.putExtra("user_name", query.getResponseData(index.get(position), "user_name"));
					cintent.putExtra("registration_id", query.getResponseData(index.get(position), "transaction_id"));
					cintent.putExtra("battle_id", query.getResponseData(index.get(position), "battle_id"));
					
					// インデックスをクリア
					index.clear();
				}
				else {
					cintent.putExtra("user_mode", "cpu");
					cintent.putExtra("user_name", userList[position].getName());
				}
				
				// 次画面へ遷移
				startActivity(cintent);
				
				// リスト表示終了
				ad.cancel();

			}
		});

	}

    
    
    
	private Handler mHandlerAccessLogTask = new Handler();
	public void init() {
		
		// GCMへ端末情報を登録する
		OnlineUtil.registerDevice(this);
		
		// アクセスログの投入
		final Context context = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 10; i++) {
						final String regId = GCMRegistrar.getRegistrationId(context);
						Log.d("★", "registrationid = " + regId);
						if (!regId.equals("")) {
							
							mHandlerAccessLogTask.post(new Runnable() {
								public void run() {
									OnlineQueryAccessLog query = new OnlineQueryAccessLog();
									query.setUserId(StatusInfo.getUserId(context));
									query.setUserName(StatusInfo.getUserName(context));
									query.setLevel(StatusInfo.getLevel(context));
									query.setRegistrationId(regId);
									new OnlineOneTimeTask(context).execute(query);
								}
							});
							break;
						}
						Thread.sleep(2000);
						
						if (i == 9) {
							Log.d("★", " failure gettting registrationid");
						}
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

//		try {
//			String response = OnlineConnection.post(query);
//			Log.d("StartActivity", response);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}

}
