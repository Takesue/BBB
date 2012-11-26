package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.ict.apps.bobb.common.StatusInfo;

/**
 * ユーザ名を登録する
 */
public class OnlineQueryUserRegister extends OnlineQuery {
	
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
	
	@Override
	public void execAfterReceiveingAction(Context context) {
		// 受信後にリファレンスにデータを保存する処理を呼び出す必要あり。
		StatusInfo.setUserId(context, this.getResponseData(0, "id"));
	}
	
	/**
	 * ユーザ名を設定する
	 * @param name
	 */
	public void setUserName(String name) {
		this.reqParams.put("user_name", name);
	}
}
