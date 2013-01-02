package com.ict.apps.bobb.battle.effect;

import com.ict.apps.bobb.battle.player.Player;

public class EffectDoubleDefense extends EffectOfCard {

	@Override
	public void execEffect(Player userInfo, Player enemyInfo) {
		
		// 守備力2倍
		userInfo.totalDefense = userInfo.totalDefense * 2;
		
	}

	@Override
	public void execEffectAnimation(Player userInfo, Player enemyInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
