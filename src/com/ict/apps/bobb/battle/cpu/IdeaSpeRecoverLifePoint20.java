package com.ict.apps.bobb.battle.cpu;

import com.ict.apps.bobb.data.Card;

/**
 * 特殊カード使用判断クラス
 * ライフポイントカードを使うべきかどうかを判断する
 *
 */
public class IdeaSpeRecoverLifePoint20 extends IdeaForSelectSpeCard {
	
	@Override
	protected Card[] judge(Object info) {
		
		// カードを保持しているかどうか確認
		
		// 持っていれば使用するべきかどうか判断する。
		return null;
	}
	
	@Override
	protected int getEffectId() {
		return IdeaForSelectSpeCard.EFFECTID_LP_RECOVER;
	}

}
