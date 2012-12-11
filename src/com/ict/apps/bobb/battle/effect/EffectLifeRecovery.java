package com.ict.apps.bobb.battle.effect;

import com.ict.apps.bobb.battle.player.Player;



/**
 * ライフポイント回復クラス
 */
public class EffectLifeRecovery extends EffectOfCard {

	@Override
	public void execEffect(Player userInfo, Player enemyInfo) {
		
		// 30%ライフポイント増
		userInfo.setLifepoint((int)(userInfo.getLifepoint()*1.3f));
		
	}

	@Override
	public void execEffectAnimation(Player userInfo, Player enemyInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
