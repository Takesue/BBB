package com.ict.apps.bobb.battle.cpu;

import java.util.ArrayList;

import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.data.Card;

/**
 * カード選択のための判断クラス(抽象クラス)
 * 
 */
public abstract class IdeaForSelectCard {

	private IdeaForSelectCard next = null;
	
	/**
	 * 次案取得
	 */
	public IdeaForSelectCard getNext() {
		return this.next;
	}
	
	public IdeaForSelectCard setNext(IdeaForSelectCard next) {
		this.next = next;
		return next;
	}
	
	/**
	 * 使用するカードを選択する
	 * @return 使用するカードの配列を返却する。
	 */
	public ArrayList<BattleCardView> choiceCard(CardBattlerInfo userInfo, CardBattlerInfo enemyInfo) {

		ArrayList<BattleCardView> selectedCards = this.judge(userInfo, enemyInfo);
		
		if(selectedCards == null) {
			// 判断できなかったため、次のIdeaを検討する。
			if (this.next != null) {
				selectedCards = this.next.choiceCard(userInfo, enemyInfo);
			}
		}
		return selectedCards;
	}
	
	
	/**
	 * アイデア採用時には真を返す
	 * @return
	 */
	protected abstract ArrayList<BattleCardView> judge(CardBattlerInfo userInfo, CardBattlerInfo enemyInfo);
	

}
