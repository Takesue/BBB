package com.ict.apps.bobb.bobbactivity;

import java.util.ArrayList;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.battle.player.CPU01;
import com.ict.apps.bobb.battle.player.CPU02;
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
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQuery;
import com.ict.apps.bobb.online.OnlineQueryOnlineUserList;
import com.ict.apps.bobb.online.OnlineQueryUserRegister;
import com.ict.apps.bobb.online.OnlineResponseListener;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    
    
	public void battleCpuOnClick(View v) {

		final Player[] userList = {
			new CPU01(this),
			new CPU02(this)
		};

		this.viewPopupPlayerLis(userList);
// 		Intent bintent = new Intent(MainMenuActivity.this, BattleUserSelectActivity.class);
// 		startActivity(bintent);

	}
	
	private OnlineQueryOnlineUserList query = null;

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

		// Intent cintent = new Intent(MainMenuActivity.this,
		// BattleUserSelectActivity.class);
		// startActivity(cintent);

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
				Intent cintent = new Intent(MainMenuActivity.this, BattleActivity.class);
				
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


}
