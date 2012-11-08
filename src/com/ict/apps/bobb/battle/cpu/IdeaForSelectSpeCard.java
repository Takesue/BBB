package com.ict.apps.bobb.battle.cpu;

import com.ict.apps.bobb.data.Card;

/**
 * 特殊カードを選択するための判断クラス（抽象クラス）
 *
 */
public abstract class IdeaForSelectSpeCard {
	
	// 効果カード種別
	public static final int EFFECTID_LP_RECOVER = 1;
	public static final int EFFECTID_LP_ATTACK_UP_10 = 2;
	
	private IdeaForSelectSpeCard next = null;
	
	/**
	 * 次案取得
	 */
	public IdeaForSelectSpeCard getNext() {
		return this.next;
	}
	
	public IdeaForSelectSpeCard setNext(IdeaForSelectSpeCard next) {
		this.next = next;
		return next;
	}
	
	/**
	 * 使用するカードを選択する
	 * @return 効果ID
	 */
	public Card[] choiceCard(Object info) {

		Card[] cards = this.judge(info);

		if(cards == null) {
			if (this.next != null) {
				cards = this.next.choiceCard(info);
			}
		}
		return cards;
	}
	
	
	/**
	 * アイデア採用時には効果対象のを返す
	 * @return
	 */
	protected abstract Card[] judge(Object info);
	
	/**
	 * 効果IDを取得する
	 * @return
	 */
	protected abstract int getEffectId();
	

}
