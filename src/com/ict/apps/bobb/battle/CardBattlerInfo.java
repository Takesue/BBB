package com.ict.apps.bobb.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.ict.apps.bobb.bobbactivity.BattleCardView;

/**
 * 対戦者の情報を一括管理するクラス
 */
public class CardBattlerInfo {

	private String name = null;
	
	private int lifepoint = 0;
	
	// 使用するカードを３０枚保持
	private ArrayList<BattleCardView> cardList = new ArrayList<BattleCardView>();
	
	// 状況　未配布：0　手札：１　選択：２ 　使用済：3
	private HashMap<BattleCardView, Integer> cardStatusList = new HashMap<BattleCardView, Integer>();
	
	// 現時点の配布予定カードのindex
//	private int curPos = 0;
	public int curPos = 0;
	
	
	public CardBattlerInfo() {
	}
	
	/**
	 * バトルカードを登録
	 * @param card
	 */
	public void setBattleCards(BattleCardView card) {
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
	public BattleCardView getNextCard() {
		BattleCardView card = this.cardList.get(this.curPos++);
		this.dealCard(card);
		return card;
	}
	
	/**
	 * 状況を未配布→手札へ変更
	 */
	public void dealCard(BattleCardView card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 1);
		
	}
	
	/**
	 * 状況を手札から選択へ変更
	 */
	public void selectCard(BattleCardView card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 2);
		
	}

	/**
	 * 状況を使用済みへ変更
	 */
	public void dustCard(BattleCardView card) {
		// ステータスを手札に上書き
		this.cardStatusList.put(card, 3);
		
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
			
			// カードの状況＝１or2のカードを集める
			BattleCardView card = this.cardList.get(i);
			if((this.getStatus(card) == 1) || (this.getStatus(card) == 2)){
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
			
			// カードの状況＝１or2のカードを集める
			BattleCardView card = this.cardList.get(i);
			if(this.getStatus(card) == 2){
				newList.add(card);
			}
		}
		
		return newList;
	}

	
	/**
	 * 山札のカードを渡す
	 * @return
	 */
	public ArrayList<BattleCardView> getUnUsedCard() {
		
		ArrayList<BattleCardView> newList = new ArrayList<BattleCardView>();
		
		int length = this.cardList.size();
		for (int i = 0; i < length; i++) {
			
			// カードの状況＝0のカードを集める
			BattleCardView card = this.cardList.get(i);
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
	public Integer getStatus(BattleCardView card) {
		return this.cardStatusList.get(card);
	}

	/**
	 * 残りの枚数
	 * @return
	 */
	public int getCounter() {
		return this.cardList.size() - this.curPos;
	}
	
	/**
	 * カードをシャッフルする。
	 */
	public void shuffle() {
		
		ArrayList<BattleCardView> list = this.getAllCards();
		
		ArrayList<BattleCardView> tmpList = new ArrayList<BattleCardView>();
		Random random = new Random();
		
		while( list.size() > 0 ) {
			int r = random.nextInt( list.size() );
			tmpList.add( list.remove(r) );
		}
		
		for (BattleCardView card : tmpList) {
			list.add(card);
		}
	}
	
	/**
	 * 山札のカードの枚数を取得
	 * @return
	 */
	public int getUnUsedCardCount() {
				
		int counter = 0;
		int length = this.cardList.size();
		for (int i = 0; i < length; i++) {
			
			// カードの状況＝0のカードを集める
			BattleCardView card = this.cardList.get(i);
			if(this.getStatus(card) == 0){
				counter++;
			}
		}
		
		return counter;
	}


}
