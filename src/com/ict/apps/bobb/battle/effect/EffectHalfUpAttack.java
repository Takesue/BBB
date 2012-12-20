package com.ict.apps.bobb.battle.effect;

import com.ict.apps.bobb.battle.player.Player;

/**
 * 攻撃力５０％UP
 */
public class EffectHalfUpAttack extends EffectOfCard {

	@Override
	public void execEffect(Player userInfo, Player enemyInfo) {
		
		// 攻撃力1.5倍
		userInfo.totalAttack = (int)(userInfo.totalAttack * 1.5f);
		
	}

	@Override
	public void execEffectAnimation(Player userInfo, Player enemyInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
