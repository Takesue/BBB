package com.ict.apps.bobb.battle.player;

import com.ict.apps.bobb.battle.CardInfo;
import com.ict.apps.bobb.battle.SpecialCardInfo;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 初期対戦用CPU
 */
public class CPU02 extends CPU {
	
	/**
	 * コンストラクタ
	 */
	public CPU02(Context context) {
		super(context);
		
		this.setName("CPU02");
		this.setLifepoint(Integer.parseInt((String)context.getText(R.string.maxLp)));
		this.setLpMax(this.getLifepoint());
		this.setLevel(2);
		
		// CPU思考回路の設定
		// 判断の優先度順に設定する。
		this.setIdeaList(new IdeaAttributeCombo());
		this.setIdeaList(new IdeaFirstInFirstout());
		
	}

	@Override
	public void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards) {
		
		this.cardInfo = new CardInfo();
		this.specialInfo = new SpecialCardInfo();
		
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

		
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit1.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit2.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit3.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit4.createBeetleCards());
		this.setCardInfoToCardBattlerInfo((BeetleCard[]) beetlekit5.createBeetleCards());
		this.setCardInfoToCardSpecialInfo((SpecialCard[]) specialkit1.createBeetleCards());
		this.setCardInfoToCardSpecialInfo((SpecialCard[]) specialkit2.createBeetleCards());
		this.setCardInfoToCardSpecialInfo((SpecialCard[]) specialkit3.createBeetleCards());

		return;
	}
	
	/**
	 * 対戦時に使用するカードを生成する
	 */
	@Override
	public void createCardBattlerInfo() {
		this.createCardBattlerInfo(null, null);
	}


}
