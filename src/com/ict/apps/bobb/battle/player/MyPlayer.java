package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;


import com.ict.apps.bobb.battle.CardInfo;
import com.ict.apps.bobb.battle.SpecialCardInfo;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;

public class MyPlayer extends Player {


	/**
	 * コンストラクタ
	 * @param context
	 */
	public MyPlayer(Context context) {
		super(context);
	}

	@Override
	public void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards) {
		return;
	}
	
	@Override
	public void createCardBattlerInfo() {
		// ユーザの対戦時情報を管理する管理テーブルに設定する
		this.setName(StatusInfo.getUserName(this.context));
		this.setLifepoint(StatusInfo.getLP(this.context));
		this.setLpMax(this.getLifepoint());
		this.cardInfo = new CardInfo();
		this.specialInfo = new SpecialCardInfo();
		
		// 戦闘時使用キットクラスの使用例
		BeetleKit beetlekit1 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK1);
		BeetleKit beetlekit2 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK2);
		BeetleKit beetlekit3 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK3);
		BeetleKit beetlekit4 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK4);
		BeetleKit beetlekit5 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK5);
		BeetleKit specialkit1 = BattleUseSpecialCard.getUseKit(this.context, BattleUseSpecialCard.CardNum.CARD1);
		BeetleKit specialkit2 = BattleUseSpecialCard.getUseKit(this.context, BattleUseSpecialCard.CardNum.CARD2);
		BeetleKit specialkit3 = BattleUseSpecialCard.getUseKit(this.context, BattleUseSpecialCard.CardNum.CARD3);
		
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit1.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit2.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit3.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit4.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit5.createBeetleCards());
		this.setCardInfoToCardSpecialInfo((SpecialCard[]) specialkit1.createBeetleCards());
		this.setCardInfoToCardSpecialInfo((SpecialCard[]) specialkit2.createBeetleCards());
		this.setCardInfoToCardSpecialInfo((SpecialCard[]) specialkit3.createBeetleCards());
	}

	@Override
	public ArrayList<BattleCardView> getSelectCard(Player userInfo,
			Player enemyInfo) {
		return null;
	}

	@Override
	public ArrayList<BattleCardView> getSelectSpacialCard(
			Player userInfo, Player enemyInfo) {
		return null;
	}

	/**
	 * 強制的にカードを選択する。
	 */
	public void forcedCardSelection() {
		
		// 選択済みのカードの枚数から不足分の枚数を確定
		int shortageNum = 3 - this.cardInfo.getSelectedCard().size();
		
		// 不足カードがある場合、カードを補填する。
		if (shortageNum > 0) {
			// 選択済みも含めた手札を取得
			ArrayList<BattleCardView> holdCards = this.cardInfo.getHoldCard();
			
			// 3枚選択していない場合に不足枚数分のカードを強制的にカードを選択ステータスにする。
			int length = holdCards.size();
			for (int i = 0; i < length; i++) {
				
				BattleCardView card = holdCards.get(i);
				if (this.cardInfo.getStatus(card) == 1) {
					// 手札ステータスの場合、選択済みステータスにする
					this.cardInfo.selectCard(card);
					
					// 不足数カウンタをデクリメントする
					shortageNum--;
				}
				
				// 選択カード不足数の確認
				if (shortageNum == 0) {
					// 不足分の追加が終わったらループ終了
					break;
				}
			}
		}
	}

	
}
