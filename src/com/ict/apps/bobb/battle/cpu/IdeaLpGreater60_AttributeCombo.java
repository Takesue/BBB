
package com.ict.apps.bobb.battle.cpu;

import java.util.ArrayList;

import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.data.Card;

/**
 * カード選択アイデアクラス
 * 
 * 自分のLP状態が80%以上の場合、
 * 手持ちカード内で属性発動優先できなければ次Ideaへ
 *
 */
public class IdeaLpGreater60_AttributeCombo extends IdeaForSelectCard {

	private int lpmax = 4000;
	
	@Override
	protected ArrayList<BattleCardView> judge(CardBattlerInfo userInfo, CardBattlerInfo enemyInfo) {
		
		ArrayList<BattleCardView> cardList = null;
		
		if (enemyInfo.getLifepoint() >= lpmax*60/100) {
			cardList = new IdeaAttributeCombo().judge(userInfo, enemyInfo);
		}
		
		return cardList;
	}

}