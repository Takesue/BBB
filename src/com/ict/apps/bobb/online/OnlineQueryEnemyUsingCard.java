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
 * 対戦相手カード情報取得要求
 */
public class OnlineQueryEnemyUsingCard extends OnlineQuery {

	// HTTPリクエストのパラメタ値
	public Map<String, String> reqParams = new HashMap<String, String>();

	@Override
	public String getServerURL() {
		return OnlineQuery.SERVER_URL + "/enemy_using_card";
	}

	@Override
	public Map<String, String> getParam() {
		return this.reqParams;
	}

	@Override
	public void execAfterReceiveingAction(Context context) {
		// 対戦相手カード情報取得要求結果処理
		((BattleActivity)context).bm.responseEnemyUsingCard();
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
	 * 相手使用カードの配列
	 * @return
	 */
	public BeetleCard[] getBeetleCards() {
		
		ArrayList<BeetleCard> cardsList = new ArrayList<BeetleCard>();
		int length = this.getResponseRecordCount();
		for (int i = 0; i < length; i++) {
			
			if ( "3".equals(this.getResponseData(i, "cardtype")) 
					|| "4".equals(this.getResponseData(i, "cardtype"))) {
				BeetleCard card = new BeetleCard();
				
				card.setCardNum(Integer.parseInt(this.getResponseData(i, "card_num")));
				card.setBeetleKitId(Integer.parseInt(this.getResponseData(i, "beetlekit_id")));
				card.setImageId(Integer.parseInt(this.getResponseData(i, "image_id")));
				card.setImageFileName(this.getResponseData(i, "image_file_name"));
				card.setName(this.getResponseData(i, "beetle_name"));
				card.setType(Integer.parseInt(this.getResponseData(i, "cardtype")));
				card.setIntroduction(this.getResponseData(i, "intro"));
				card.setAttack(Integer.parseInt(this.getResponseData(i, "attack")));
				card.setDefense(Integer.parseInt(this.getResponseData(i, "defense")));
				card.setAttribute("1".equals(this.getResponseData(i, "cardattr")) 
						? CardAttribute.FIRE : "2".equals(this.getResponseData(i, "cardattr")) 
						? CardAttribute.WATER : CardAttribute.WIND);
				cardsList.add(card);
			}
		}
		
		return cardsList.toArray(new BeetleCard[cardsList.size()]);
	}
	
	/**
	 * 相手使用カードの配列
	 * @return
	 */
	public SpecialCard[] getSpecialCards() {
		
		ArrayList<SpecialCard> cardsList = new ArrayList<SpecialCard>();
		int length = this.getResponseRecordCount();
		for (int i = 0; i < length; i++) {
			
			if ( "2".equals(this.getResponseData(i, "cardtype"))) {
				SpecialCard card = new SpecialCard();
				
				card.setCardNum(Integer.parseInt(this.getResponseData(i, "card_num")));
				card.setBeetleKitId(Integer.parseInt(this.getResponseData(i, "beetlekit_id")));
				card.setImageId(Integer.parseInt(this.getResponseData(i, "image_id")));
				card.setImageFileName(this.getResponseData(i, "image_file_name"));
				card.setName(this.getResponseData(i, "beetle_name"));
				card.setType(Integer.parseInt(this.getResponseData(i, "cardtype")));
				card.setIntroduction(this.getResponseData(i, "intro"));
				card.setEffect(this.getResponseData(i, "effect"));
				card.setEffectId(Integer.parseInt(this.getResponseData(i, "effect_id")));
				cardsList.add(card);
			}
		}
		
		return cardsList.toArray(new SpecialCard[cardsList.size()]);
	}


}
