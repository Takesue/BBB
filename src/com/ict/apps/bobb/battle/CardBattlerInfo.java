package com.ict.apps.bobb.battle;

import java.util.ArrayList;
import java.util.HashMap;

import com.ict.apps.bobb.bobbactivity.BattleCards;

/**
 * 対戦者の情報を一括管理するクラス
 */
public class CardBattlerInfo {

	private String name = null;
	
	private int lifepoint = 0;
	
	// 使用するカードを３０枚保持
	private ArrayList<BattleCards> cardList = new ArrayList<BattleCards>();
	
	// 状況　未配布：0　手札：１　選択：２ 　使用済：3
	private HashMap<BattleCards, Integer> cardStatusList = new HashMap<BattleCards, Integer>();
	
	// 現時点の配布予定カードのindex
	private int curPos = 0;
	
	
	public CardBattlerInfo() {
	}
	
	/**
	 * バトルカードを登録
	 * @param card
	 */
	public void setBattleCards(BattleCards card) {
		this.cardList.add(card);
		this.cardStatusList.put(card, 0);
		
	}
	
	/**
	 * 名前を取得する
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名前を設定する
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ライフポイントを取得する
	 * @return
	 */
	public int getLifepoint() {
		return lifepoint;
	}

	/**
	 * ライフポイントを設定する
	 * @param lifepoint
	 */
	public void setLifepoint(int lifepoint) {
		this.lifepoint = lifepoint;
	}
	
	/**
	 * 山札にあるカードから次の配布カードを取得
	 * @return
	 */
	public BattleCards getNextCard() {
		BattleCards card = this.cardList.get(this.curPos++);
		this.dealCard(card);
		return card;
	}
	
	/**
	 * 状況を未配布→手札へ変更
	 */
	public void dealCard(BattleCards card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 1);
		
	}
	
	/**
	 * 状況を手札から選択へ変更
	 */
	public void selectCard(BattleCards card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 2);
		
	}

	/**
	 * 状況を使用済みへ変更
	 */
	public void dustCard(BattleCards card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 3);
		
	}

	/**
	 * カードビューオブジェクトをすべて渡す
	 */
	public ArrayList<BattleCards> getAllCards() {
		return this.cardList;
	}
	
	/**
	 * 手札のカードを渡す
	 * @return
	 */
	public ArrayList<BattleCards> getHoldCard() {
		
		ArrayList<BattleCards> newList = new ArrayList<BattleCards>();
		
		int length = this.cardList.size();
		for (int i = 0; i < length; i++) {
			
			// カードの状況＝１or2のカードを集める
			BattleCards card = this.cardList.get(i);
			if((this.getStatus(card) == 1) || (this.getStatus(card) == 2)){
				newList.add(card);
			}
		}
		
		return newList;
	}
	
	/**
	 * 山札のカードを渡す
	 * @return
	 */
	public ArrayList<BattleCards> getUnUsedCard() {
		
		ArrayList<BattleCards> newList = new ArrayList<BattleCards>();
		
		int length = this.cardList.size();
		for (int i = 0; i < length; i++) {
			
			// カードの状況＝0のカードを集める
			BattleCards card = this.cardList.get(i);
			if(this.getStatus(card) == 0){
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
	public int getStatus(BattleCards card) {
		return this.cardStatusList.get(card);
	}

	/**
	 * 残りの枚数
	 * @return
	 */
	public int getCounter() {
		return this.cardList.size() - this.curPos;
	}

}
