package com.ict.apps.bobb.battle.cpu;

import java.util.ArrayList;

import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.bobbactivity.BattleCardView;

/**
 * 対戦相手のインターフェース
 */
public interface Player {
	
	/**
	 * 一般カードを選択する
	 * @return  カードを3枚保持する配列、nullの場合選択失敗
	 */
	public ArrayList<BattleCardView> getSelectCard(CardBattlerInfo userInfo, CardBattlerInfo enemyInfo);
	
	/**
	 * 特殊カードを選択する
	 * @return 特殊カード未使用の場合null
	 */
	public ArrayList<BattleCardView> getSelectSpacialCard(CardBattlerInfo userInfo, CardBattlerInfo enemyInfo);

}
