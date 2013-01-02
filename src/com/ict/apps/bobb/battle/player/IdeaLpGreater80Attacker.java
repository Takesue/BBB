package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;


import com.ict.apps.bobb.bobbactivity.BattleCardView;

public class IdeaLpGreater80Attacker extends IdeaForSelectCard {

	int lp = 0;
	int lpmax = 0;

	@Override
	protected ArrayList<BattleCardView> judge(Player userInfo, Player enemyInfo) {
		ArrayList<BattleCardView> cardList = null;
		
		if (lp >= lpmax*80/100) {
			cardList = new IdeaAttributeCombo().judge(userInfo, enemyInfo);
		}
		
		return cardList;
	}

}
