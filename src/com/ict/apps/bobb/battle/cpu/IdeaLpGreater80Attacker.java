package com.ict.apps.bobb.battle.cpu;

import com.ict.apps.bobb.data.Card;

public class IdeaLpGreater80Attacker extends IdeaForSelectCard {

	int lp = 0;
	int lpmax = 0;

	@Override
	protected Card[] judge(Object info) {
		Card[] cardList = null;
		
		if (lp >= lpmax*80/100) {
			cardList = new IdeaAttributeCombo().judge(info);
		}
		
		return cardList;
	}

}
