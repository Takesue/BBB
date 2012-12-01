package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict.apps.bobb.bobbactivity.BattleActivity;

import android.content.Context;

/**
 * 対戦ステータス確認
 */
public class OnlineQueryBattleStatus extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();
	
	private String loopFinishStatus = "0";

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/battle_status";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}

	@Override
	public void execAfterReceiveingAction(Context context) {
		((BattleActivity)context).bm.readyBattle();
	}

	@Override
	public boolean isPoolingFinish(String response) {
		boolean result = false;
		
		try {
			JSONObject object = new JSONObject(response);
			String status = object.getString("battle_status");
			
			if (this.loopFinishStatus.equals(status)) {
				// ポーリング終了条件の文字列と一致したらポーリング終了する
				result = true;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 対戦IDを設定する
	 * @param name
	 */
	public void setBattleId(String id) {
		this.reqParams.put("battle_id", id);
	}
	
	/**
	 * Pooling終了となるためのステータス条件の値を設定する。デフォルトは”０”
	 * @param loopFinishStatus
	 */
	public void setLoopFinishStatus(String loopFinishStatus) {
		this.loopFinishStatus = loopFinishStatus;
	}


}
