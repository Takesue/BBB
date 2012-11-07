package com.ict.apps.bobb.cpu;

import com.ict.apps.bobb.data.Card;

/**
 * カード選択アイデアクラス
 * 
 * 自分のLP状態が60%以上の場合、
 * 手持ちカード内で属性発動優先できなければ次Ideaへ
 *
 */
public class IdeaLpGreater60_AttributeCombo extends IdeaForSelectCard {

	private int lp = 0;
	private int lpmax = 0;
	
	@Override
	protected Card[] judge(Object info) {
		
		Card[] cardList = null;
		
		if (lp >= lpmax*80/100) {
			cardList = new IdeaAttributeCombo().judge(info);
		}
		
		return cardList;
	}

}
