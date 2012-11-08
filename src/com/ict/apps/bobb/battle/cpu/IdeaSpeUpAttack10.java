package com.ict.apps.bobb.battle.cpu;

import com.ict.apps.bobb.data.Card;

public class IdeaSpeUpAttack10 extends IdeaForSelectSpeCard {

	@Override
	protected int getEffectId() {
		return IdeaForSelectSpeCard.EFFECTID_LP_ATTACK_UP_10;
	}

	@Override
	protected Card[] judge(Object info) {
		return null;
	}

}
