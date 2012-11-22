package com.ict.apps.bobb.battle.effect;

import com.ict.apps.bobb.battle.CardBattlerInfo;

/**
 * ライフポイント回復クラス
 */
public class EffectLifeRecovery extends EffectOfCard {

	@Override
	public void execEffect(CardBattlerInfo userInfo, CardBattlerInfo enemyInfo) {
		
		// 30%ライフポイント増
		userInfo.setLifepoint((int)(userInfo.getLifepoint()*1.3f));
		
	}
}
