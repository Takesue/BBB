package com.ict.apps.bobb.online;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.CardAttribute;
import com.ict.apps.bobb.data.SpecialCard;

import android.content.Context;

/**
 * 対戦時選択カード情報取得
 */
public class OnlineQueryEnemySelectedCard extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/enemy_selected_card";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}

	@Override
	public void execAfterReceiveingAction(Context context) {
		// 対戦相手カード情報取得要求結果処理
		((BattleActivity)context).bm.responseEnemySelectedCard();
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
	
	/**
	 * ターン番号を設定する
	 * @param num
	 */
	public void setTurnNum(int num) {
		this.reqParams.put("turn_num", Integer.toString(num));
	}

	/**
	 * 相手使用カード番号の配列
	 * @return
	 */
	public Integer[] getSelectedCardNum() {
		
		int length = this.getResponseRecordCount();
		
		ArrayList<Integer> cardNums = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			
			int num = Integer.parseInt(this.getResponseData(i, "card_num"));
			if ((0 < num ) && (num <= 30)){
				cardNums.add(num);
			}
		}
		Integer[] cardNumList = new Integer[cardNums.size()];

		return cardNums.toArray(cardNumList);
	}
	
	/**
	 * 相手使用特殊カード番号の配列
	 * @return
	 */
	public Integer[] getSelectedSpecialCardNum() {
		
		int length = this.getResponseRecordCount();
		
		ArrayList<Integer> cardNums = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			
			int num = Integer.parseInt(this.getResponseData(i, "card_num"));
			if ((1000 < num ) && (num <= 1003)){
				cardNums.add(num);
			}
		}
		
		Integer[] cardNumList = new Integer[cardNums.size()];

		return cardNums.toArray(cardNumList);
	}


}
