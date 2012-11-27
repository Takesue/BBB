package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * アクセスログを登録する
 */
public class OnlineQueryBattleReqest extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/request_battle";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}

	@Override
	public void execAfterReceiveingAction(Context context) {
		// 受信後特になにもしない。
	}

	/**
	 * ユーザIDを設定する
	 * @param name
	 */
	public void setUserId(String id) {
		this.reqParams.put("req_user_id", id);
	}

	/**
	 * ユーザIDを設定する
	 * @param name
	 */
	public void setEnemyUserId(String id) {
		this.reqParams.put("res_user_id", id);
	}
	
	/**
	 * registrationIdを設定する
	 * @param name
	 */
	public void setRegistrationId(String registrationId) {
		this.reqParams.put("transaction_id", registrationId);
	}

}
