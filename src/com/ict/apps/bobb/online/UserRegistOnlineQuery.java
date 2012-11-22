package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

/**
 * ユーザ名を登録する
 */
public class UserRegistOnlineQuery implements OnlineQuery {

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
	 * ユーザ名を設定する
	 * @param name
	 */
	public void setUserName(String name) {
		this.reqParams.put("user_name", name);
	}

}
