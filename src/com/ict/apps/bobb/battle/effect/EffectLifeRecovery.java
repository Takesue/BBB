package com.ict.apps.bobb.battle.effect;

import com.ict.apps.bobb.battle.player.Player;



/**
 * ライフポイント回復クラス
 */
public class EffectLifeRecovery extends EffectOfCard {

	@Override
	public void execEffect(Player userInfo, Player enemyInfo) {
		
		// 50%ライフポイント増
		userInfo.setLifepoint((int)(userInfo.getLifepoint() + userInfo.getLpMax()*0.5f));
		
		if (userInfo.getLifepoint() > userInfo.getLpMax()) {
			// MAX値よりも大きい場合、MAX値へ変更
			userInfo.setLifepoint(userInfo.getLpMax());
		}
		
		
	}

	@Override
	public void execEffectAnimation(Player userInfo, Player enemyInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
