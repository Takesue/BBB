package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import com.ict.apps.bobb.bobbactivity.BattleActivity;

import android.content.Context;

/**
 * 対戦要求をする
 */
public class OnlineQueryResponseForBattleReq extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/response_battlereq";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}

	@Override
	public void execAfterReceiveingAction(Context context) {
		// 対戦要求送信成功
		((BattleActivity)context).bm.successBattleRequest();
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
	

}
