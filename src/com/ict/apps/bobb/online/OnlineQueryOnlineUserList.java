package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import com.ict.apps.bobb.bobbactivity.MainMenuActivity;

import android.content.Context;

/**
 * 対戦ユーザ一覧要求
 */
public class OnlineQueryOnlineUserList extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/online_user_list";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}

	@Override
	public void execAfterReceiveingAction(Context context) {
		
		if (context instanceof MainMenuActivity) {
			// Qery発行がMainMenuActivityの場合
			// Qery終了時に以下メソッドを呼び出す
			((MainMenuActivity)context).viewPopupUserLis();
		}
		
	}
	
	@Override
	public boolean isPoolingFinish(String response) {
		return true;
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
	
}
