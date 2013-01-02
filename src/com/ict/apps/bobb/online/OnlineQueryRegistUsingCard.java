package com.ict.apps.bobb.online;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.MainMenuActivity;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.CardAttribute;
import com.ict.apps.bobb.data.SpecialCard;

import android.content.Context;
import android.util.Log;

/**
 * 対戦使用カード情報登録
 */
public class OnlineQueryRegistUsingCard extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/regist_using_card";
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
	 * @param name
	 */
	public void setUserId(String id) {
		this.reqParams.put("user_id", id);
	}

	/**
	 * 対戦IDを設定する
	 * @param name
	 */
	public void setBattleId(String id) {
		this.reqParams.put("battle_id", id);
	}
	
	
	private JSONArray jsonArray = new JSONArray();

	/**
	 * 虫カードオブジェクトの情報を設定する
	 * @param card
	 */
	public void setCardInfoList(BeetleCard[] cards) {
		
		try {
			for (BeetleCard card: cards) {
				JSONObject jsonMap = new JSONObject();
				jsonMap.put("card_num", card.getCardNum());
				jsonMap.put("beetlekit_id", card.getBeetleKitId());
				jsonMap.put("image_id", card.getImageId());
				jsonMap.put("image_file_name", card.getImageFileName());
				jsonMap.put("beetle_name", card.getName());
				jsonMap.put("cardtype", card.getType());
				jsonMap.put("intro", card.getIntroduction());
				jsonMap.put("attack", card.getAttack());
				jsonMap.put("defense", card.getDefense());
				jsonMap.put("cardattr", card.getAttribute() == CardAttribute.FIRE 
						? "1" : card.getAttribute() == CardAttribute.WATER 
						? "2" : "3" );
				jsonMap.put("effect", " ");
				jsonMap.put("effect_id", " ");
				
				this.jsonArray.put(jsonMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 特殊カードオブジェクトの情報を設定する
	 * @param card
	 */
	public void setSpecialCardInfoList(SpecialCard[] cards) {

		try {
			for (SpecialCard card: cards) {
				JSONObject jsonMap = new JSONObject();
				jsonMap.put("card_num", card.getCardNum());
				jsonMap.put("beetlekit_id", card.getBeetleKitId());
				jsonMap.put("image_id", card.getImageId());
				jsonMap.put("image_file_name", card.getImageFileName());
				jsonMap.put("beetle_name", card.getName());
				jsonMap.put("cardtype", card.getType());
				jsonMap.put("intro", card.getIntroduction());
				jsonMap.put("attack", "0");
				jsonMap.put("defense", "0");
				jsonMap.put("cardattr", " ");
				jsonMap.put("effect", card.getEffect());
				jsonMap.put("effect_id", card.getEffectId());
				
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
