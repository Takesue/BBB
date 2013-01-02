package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;


import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;

/**
 * オンラインでの対戦相手クラス
 * バックグラウンドでPoolingして、対戦相手の情報を採取します。
 */
public class OnlinePlayer extends Player{

	/**
	 * コンストラクタ
	 * @param context
	 */
	public OnlinePlayer(Context context) {
		super(context);
		this.setLifepoint(StatusInfo.getLP(context));
	}

	/**
	 * 一般カードを選択する
	 * @return  カードを3枚保持する配列、nullの場合選択失敗、適当に上位で設定してくださいますか？(左から順に３まいとか。）
	 *          
	 */
	public ArrayList<BattleCardView> getSelectCard(Player userInfo, Player enemyInfo) {
		// 対戦相手のカード情報を取得するタスクを呼び出す
		
		return new ArrayList<BattleCardView>();
	}
	
	/**
	 * 特殊カードを選択する
	 * @return 特殊カード未使用の場合null
	 */
	@Override
	public ArrayList<BattleCardView> getSelectSpacialCard(Player userInfo, Player enemyInfo) {
		// カードを選ぶ
		
		// 使用するカードを設定する。。
		
		return new ArrayList<BattleCardView>();
	}

	
	@Override
	public void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards) {
		
		// 対戦時に使用するカードの一式を生成する
		this.setCardInfoToCardBattlerInfo(cards);
		this.setCardInfoToCardSpecialInfo(specialCards);
		
		return;
	}
	
	@Override
	public void createCardBattlerInfo() {
	}
	
	/**
	 * Onlineで選択した一般カードを設定する
	 */
	public void setSelectedCards(Integer[] cardNumList) {
		
		// Onlineユーザが保持しているカード情報を全て取得
		ArrayList<BattleCardView> cards = this.cardInfo.getAllCards();

		for (int i = 0; i < cardNumList.length; i++) {
			for (BattleCardView card : cards) {
				if (card.getCardInfo().getCardNum() == cardNumList[i]) {
					// Online先で選択したカード番号と一致したカードビューオブジェクトの状態を選択状態に設定する
					this.cardInfo.selectCard(card);
					Log.d("setSelectedCards　カード情報", "Num:" + card.getCardInfo().getCardNum());
					
					Log.d("setSelectedCards", "=============================");
					Log.d("setSelectedCards", "cardNum:" + card.getCardInfo().getCardNum());
					Log.d("setSelectedCards", "Name:" + card.getCardInfo().getName());
					Log.d("setSelectedCards", "type:" + card.getCardInfo().getType());
					Log.d("setSelectedCards", "attack:" + card.getCardInfo().getAttack());
					Log.d("setSelectedCards", "defense:" + card.getCardInfo().getDefense());
					Log.d("setSelectedCards", "Attribute:" + card.getCardInfo().getAttribute());
				}
			}
		}
	}
	
	/**
	 * Onlineで選択した特殊カードを設定する
	 */
	public void setSelectedSpecialCards(Integer[] cardNumList) {
		
		// Onlineユーザが対戦使用する特殊カード情報を取得
		ArrayList<BattleCardView> cards = this.specialInfo.getAllCards();

		for (int i = 0; i < cardNumList.length; i++) {
			for (BattleCardView card : cards) {
				if (card.getSpecialInfo().getCardNum() == cardNumList[i]) {
					// Online先で選択した特殊カード番号と一致したカードビューオブジェクトの状態を選択状態に設定する
					this.specialInfo.selectCard(card);
				}
			}
		}
	}



	

}
