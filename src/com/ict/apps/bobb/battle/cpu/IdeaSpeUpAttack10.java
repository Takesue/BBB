package com.ict.apps.bobb.battle.cpu;

import java.util.ArrayList;

import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.bobbactivity.BattleCardView;

public class IdeaSpeUpAttack10 extends IdeaForSelectSpeCard {

	@Override
	protected int getEffectId() {
		return IdeaForSelectSpeCard.EFFECTID_LP_ATTACK_UP_10;
	}

	@Override
	protected ArrayList<BattleCardView> judge(CardBattlerInfo userInfo, CardBattlerInfo enemyInfo) {
		return null;
	}

}
