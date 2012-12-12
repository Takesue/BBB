package com.ict.apps.bobb.online;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.MainMenuActivity;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.CardAttribute;
import com.ict.apps.bobb.data.SpecialCard;

import android.content.Context;
import android.util.Log;

/**
 * 対戦時選択カード情報登録
 */
public class OnlineQueryRegistSelectedCard extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/regist_selected_card";
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
	 * ユーザIDを設定する
	 * @param id
	 */
	public void setUserId(String id) {
		this.reqParams.put("user_id", id);
	}

	/**
	 * 対戦IDを設定する
	 * @param id
	 */
	public void setBattleId(String id) {
		this.reqParams.put("battle_id", id);
	}

	/**
	 * ターン番号を設定する
	 * @param num
	 */
	public void setTurnNum(int num) {
		this.reqParams.put("turn_num", Integer.toString(num));
	}

	
	private JSONArray jsonArray = new JSONArray();

	/**
	 * 選択した特殊虫カードオブジェクトの情報を設定する
	 * @param card
	 */
	public void setCardInfoList(ArrayList<BattleCardView> cards) {
		
		try {
			for (BattleCardView card: cards) {
				JSONObject jsonMap = new JSONObject();
				jsonMap.put("card_num", card.getCardInfo().getCardNum());
				this.jsonArray.put(jsonMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 選択した特殊カードオブジェクトの情報を設定する
	 * @param card
	 */
	public void setSpecialCardInfoList(ArrayList<BattleCardView> cards) {

		try {
			for (BattleCardView card: cards) {
				JSONObject jsonMap = new JSONObject();
				jsonMap.put("card_num", card.getSpecialInfo().getCardNum());
				
				this.jsonArray.put(jsonMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 全カードオブジェクトを設定終了時に必ず実行する必要あり
	 */
	public void finishedDataSet() {
		this.reqParams.put("beetel_card_infolist", this.jsonArray.toString());
		Log.d("OnlineQueryRegUseCards", "beetlelist:" + this.reqParams.get("beetel_card_infolist"));
	}
	
	
}
