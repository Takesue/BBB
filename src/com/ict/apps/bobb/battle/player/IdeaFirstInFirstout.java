package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;


import com.ict.apps.bobb.bobbactivity.BattleCardView;

/**
 * 手持ちで長く持っているもの順に3枚選ぶ
 */
public class IdeaFirstInFirstout extends IdeaForSelectCard {

	@Override
	protected ArrayList<BattleCardView> judge(Player userInfo, Player enemyInfo) {
		ArrayList<BattleCardView> cardList = new ArrayList<BattleCardView>();
		
		ArrayList<BattleCardView> cards = enemyInfo.cardInfo.getHoldCard();
		int length = 3;
		for (int i = 0; i < length; i++) {
			cardList.add(cards.get(i));
			// ステータスを選択済みに変更
			enemyInfo.cardInfo.selectCard(cards.get(i));
		}
		
		return cardList;
	}

}
