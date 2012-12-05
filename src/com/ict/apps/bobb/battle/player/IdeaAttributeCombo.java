package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;


import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.data.CardAttribute;

/**
 * ５枚からコンボが狙える場合はコンボをねらうアイデア
 */
public class IdeaAttributeCombo extends IdeaForSelectCard {

	@Override
	protected ArrayList<BattleCardView> judge(Player userInfo, Player enemyInfo) {
		ArrayList<BattleCardView> cardList = null;
		
		ArrayList<BattleCardView> cards = enemyInfo.cardInfo.getHoldCard();
		
		CardAttribute combo = this.getComboAttribute(cards);
		
		// コンボ可能な3枚を選択する
		if (combo != null) {
			// コンボがある場合
			cardList = new ArrayList<BattleCardView>();
			for(BattleCardView card : cards) {
				if (card.getCardInfo().getAttribute() == combo) {
					// 属性が一致するやつだけを抽出
					cardList.add(card);
					// ステータスを選択済みに変更
					enemyInfo.cardInfo.selectCard(card);

					if (cardList.size() == 3) {
						break;
					}
				}
			}
		}
		return cardList;
	}

	/**
	 * 手札から属性3枚揃えられるかどうか調査
	 * 揃えられるなら、属性を返す、無い場合はNULLを返却
	 * @param cards
	 */
	private CardAttribute getComboAttribute(ArrayList<BattleCardView> cards) {
		CardAttribute combo = null;
		int fireCnt = 0;
		int waterCnt = 0;
		int windCnt = 0;
		for(BattleCardView card : cards) {
			CardAttribute attr = card.getCardInfo().getAttribute();
			if (attr == CardAttribute.FIRE) {
				fireCnt++;
			}
			else if (attr == CardAttribute.WATER) {
				waterCnt++;
			}
			else if (attr == CardAttribute.WIND) {
				windCnt++;
			}
			
			// いずれかが３になったらコンポ属性に設定して、ループ終了
			if ((fireCnt == 3) || (waterCnt == 3) || (windCnt == 3)) {
				combo = (fireCnt == 3) ? CardAttribute.FIRE : (waterCnt == 3) ? CardAttribute.WATER : CardAttribute.WIND;
				break;
			}
		}
		return combo;
	}

}
