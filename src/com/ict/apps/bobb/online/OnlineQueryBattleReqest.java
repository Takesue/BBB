package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import com.ict.apps.bobb.bobbactivity.BattleActivity;

import android.content.Context;

/**
 * 対戦要求をする
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
	public boolean isPoolingFinish(String response) {
		return true;
	}

	/**
	 * 自ユーザIDを設定する
	 * @param name
	 */
	public void setUserId(String id) {
		this.reqParams.put("req_user_id", id);
	}

	/**
	 * 対戦相手ユーザIDを設定する
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
		this.reqParams.put("registration_id", registrationId);
	}

}
