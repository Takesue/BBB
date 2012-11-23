package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

/**
 * アクセスログを登録する
 */
public class OnlineQueryAccessLog extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/regist_user";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}
	
	/**
	 * ユーザIDを設定する
	 * @param name
	 */
	public void setUserId(String id) {
		this.reqParams.put("user_id", id);
	}

	/**
	 * Levelを設定する
	 * @param name
	 */
	public void setLevel(Integer level) {
		this.reqParams.put("user_level", level.toString());
	}
	
	/**
	 * registrationIdを設定する
	 * @param name
	 */
	public void setRegistrationId(String registrationId) {
		this.reqParams.put("transaction_id", registrationId);
	}
	
	

}
