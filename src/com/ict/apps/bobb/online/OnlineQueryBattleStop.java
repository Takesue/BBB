package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict.apps.bobb.bobbactivity.BattleActivity;

import android.content.Context;

/**
 * 対戦終了/中断通知
 */
public class OnlineQueryBattleStop extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();
	
	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/battle_stop";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}

	@Override
	public void execAfterReceiveingAction(Context context) {
		return;
	}

	@Override
	public boolean isPoolingFinish(String response) {
		return true;
	}

	/**
	 * 対戦IDを設定する
	 * @param name
	 */
	public void setBattleId(String id) {
		this.reqParams.put("battle_id", id);
	}
	
	/**
	 * ステータスを設定する
	 * @param name
	 */
	public void setBattleId(int status) {
		this.reqParams.put("status", String.valueOf(status));
	}



}
