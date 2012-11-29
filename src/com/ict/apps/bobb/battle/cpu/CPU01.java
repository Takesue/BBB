package com.ict.apps.bobb.battle.cpu;

import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.battle.SpecialCardInfo;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.SpecialCard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 初期対戦用CPU
 */
public class CPU01 extends CPU {
	
	/**
	 * コンストラクタ
	 */
	public CPU01(Context context) {
		super(context);
		
		// CPU思考回路の設定
		// 判断の優先度順に設定する。
		this.setIdeaSpacialList(new IdeaSpeRecoverLifePoint20());
		this.setIdeaSpacialList(new IdeaSpeUpAttack10());
		
		this.setIdeaList(new IdeaAttributeCombo());
		this.setIdeaList(new IdeaFirstInFirstout());
		
	}

	@Override
	public CardBattlerInfo createCardBattlerInfo() {
		
		CardBattlerInfo enemyInfo = new CardBattlerInfo();
		enemyInfo.setName("CPU01");
		enemyInfo.setLifepoint(4000);
		
		SpecialCardInfo enemySpecialInfo = new SpecialCardInfo();
		
		// CPUの使用する使用する虫キットを取得する
		// カードを管理テーブルに設定する。
		
		BeetleKit beetlekit1 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK1);
		BeetleKit beetlekit2 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK2);
		BeetleKit beetlekit3 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK3);
		BeetleKit beetlekit4 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK4);
		BeetleKit beetlekit5 = BattleUseKit.getUseKit(this.context, BattleUseKit.DeckNum.DECK5);
		BeetleKit specialkit1 = BattleUseSpecialCard.getUseKit(this.context, BattleUseSpecialCard.CardNum.CARD1);
		BeetleKit specialkit2 = BattleUseSpecialCard.getUseKit(this.context, BattleUseSpecialCard.CardNum.CARD2);
		BeetleKit specialkit3 = BattleUseSpecialCard.getUseKit(this.context, BattleUseSpecialCard.CardNum.CARD3);

		
		setCardInfoToCardBattlerInfo(beetlekit1, enemyInfo);
		setCardInfoToCardBattlerInfo(beetlekit2, enemyInfo);
		setCardInfoToCardBattlerInfo(beetlekit3, enemyInfo);
		setCardInfoToCardBattlerInfo(beetlekit4, enemyInfo);
		setCardInfoToCardBattlerInfo(beetlekit5, enemyInfo);
		setCardInfoToCardSpecialInfo(specialkit1, enemySpecialInfo);
		setCardInfoToCardSpecialInfo(specialkit2, enemySpecialInfo);
		setCardInfoToCardSpecialInfo(specialkit3, enemySpecialInfo);

		return enemyInfo;
	}
	
	/**
	 * 対戦デッキに設定されている虫キット情報を元に、６枚のカードを、管理テーブルに設定する
	 * @param beetlekit
	 */
	private void setCardInfoToCardBattlerInfo(BeetleKit beetlekit, CardBattlerInfo info) {
		// 取得した虫キットからカード生成
		BeetleCard[] cards = (BeetleCard[])beetlekit.createBeetleCards();
		
		BeetleCard[] sp = (BeetleCard[])beetlekit.createBeetleCards();
		
		
		for (int i = 0; i < cards.length; i++) {
			
			Integer ii = cards[i].getType();
			Log.d("cardslength", ii.toString());
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			
			viewCard.setControlActivity((BattleActivity)this.context);
			
			viewCard.setBeetleCard(cards[i]);
			
			info.setBattleCards(viewCard);
			
		}
	}
	
	/**
	 * 対戦デッキに設定されている特殊虫キット情報を元にカードを、管理テーブルに設定する
	 * @param specialkit
	 */
	private void setCardInfoToCardSpecialInfo(BeetleKit beetlekit, SpecialCardInfo info) {
		// 取得した特殊虫キットからカード生成
		SpecialCard[] cards = (SpecialCard[])beetlekit.createBeetleCards();
		
		SpecialCard[] sp = (SpecialCard[])beetlekit.createBeetleCards();
		
		
		for (int i = 0; i < cards.length; i++) {
			
			Integer ii = cards[i].getType();
			Log.d("cardslength", ii.toString());
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			
			viewCard.setControlActivity((BattleActivity)this.context);
			
			viewCard.setSpecialCard(cards[i]);
			
			info.setSpecialCards(viewCard);
			
		}
	}


}
