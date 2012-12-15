package com.ict.apps.bobb.battle.effect;

import com.ict.apps.bobb.battle.player.Player;

/**
 * 相手守備力１／２
 */
public class EffectHalfEnemyDefense extends EffectOfCard {

	@Override
	public void execEffect(Player userInfo, Player enemyInfo) {
		
		// 相手守備力１／２
		enemyInfo.totalDefense = enemyInfo.totalDefense / 2;
		
	}

	@Override
	public void execEffectAnimation(Player userInfo, Player enemyInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
