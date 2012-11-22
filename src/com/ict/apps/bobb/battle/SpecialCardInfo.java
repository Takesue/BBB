package com.ict.apps.bobb.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;

import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.data.CardAttribute;
import com.ict.apps.bobb.data.SpecialCard;

/**
 * 対戦者の特殊カード情報を一括管理するクラス
 */
public class SpecialCardInfo {

	// 使用する特殊カードを３枚保持
	private ArrayList<BattleCardView> cardList = new ArrayList<BattleCardView>();
	
	// 状況　未使用：0　選択済：1　使用済：2
	public HashMap<BattleCardView, Integer> cardStatusList = new HashMap<BattleCardView, Integer>();
	
	// 使用している特殊カードを保持
	public int spinnerId = 0;
	
	public SpecialCardInfo() {
	}
	
	/**
	 * バトルカードを登録
	 * @param card
	 */
	public void setSpecialCards(BattleCardView card) {
		this.cardList.add(card);
		this.cardStatusList.put(card, 0);
		
	}
	
	
	
	/**
	 * 状況を未使用→選択済みへ変更
	 */
	public void selectCard(BattleCardView card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 1);
		
	}
	
	/**
	 * 状況を選択済み→未使用へ変更
	 */
	public void resetCard(BattleCardView card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 0);
		
	}
	
	/**
	 * 状況を選択済み→使用済みへ変更
	 */
	public void dustCard(BattleCardView card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 2);
		
	}


	/**
	 * カードビューオブジェクトをすべて渡す
	 */
	public ArrayList<BattleCardView> getAllCards() {
		return this.cardList;
	}
	
	/**
	 * 手札のカードを渡す
	 * @return
	 */
	public ArrayList<BattleCardView> getHoldCard() {
		
		ArrayList<BattleCardView> newList = new ArrayList<BattleCardView>();
		
		int length = this.cardList.size();
		for (int i = 0; i < length; i++) {
			
			// カードの状況＝ 0 のカードを集める
			BattleCardView card = this.cardList.get(i);
			if(this.getStatus(card) == 0){
				newList.add(card);
			}
		}
		
		return newList;
	}
	
	/**
	 * 手札の内選択したカードを渡す
	 * @return
	 */
	public ArrayList<BattleCardView> getSelectedCard() {
		
		ArrayList<BattleCardView> newList = new ArrayList<BattleCardView>();
		
		int length = this.cardList.size();
		for (int i = 0; i < length; i++) {
			
			// カードの状況＝１のカードを集める
			BattleCardView card = this.cardList.get(i);
			if(this.getStatus(card) == 1){
				newList.add(card);
			}
		}
		
		return newList;
	}

	
	
	/**
	 * 引数で渡したカードの状況を取得する
	 * @param card
	 * @return
	 */
	public Integer getStatus(BattleCardView card) {
		return this.cardStatusList.get(card);
	}

	/**
	 * 山札のカードの枚数を取得
	 * @return
	 */
	public int getUnUsedCardCount() {
				
		int counter = 0;
		int length = this.cardList.size();
		for (Integer i = 0; i < length; i++) {
			
			// カードの状況＝0のカードを集める
			BattleCardView card = this.cardList.get(i);
			if(this.getStatus(card) == 0){
				counter++;
			}
			
		}
		
		return counter;
	}

	// 特殊カード使用時のステータス変更用（typeは0が使用者のため、1が相手へという意)
	public int[] judgeSpecial(int id, int type, int...total) {
		
		switch (id) {
		case 0:
			break;

		case 1:
			break;

		case 2:
			if(type == 0){
				total[0] = total[0] * 2;
			}
			break;

		case 3:
			if(type == 0){
				total[1] = total[1] * 2;
			}
			break;

		case 4:
			if(type == 1){
				total[2] = total[2] / 2;
			}
			break;

		case 5:
			if(type == 1){
				total[3] = total[3] / 2;
			}
			break;

		default:
			break;
		}
		return total;
	}

}
