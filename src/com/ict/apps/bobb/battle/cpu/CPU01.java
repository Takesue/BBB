package com.ict.apps.bobb.battle.cpu;

/**
 * 初期対戦用CPU
 */
public class CPU01 extends CPU {

	/**
	 * コンストラクタ
	 */
	public CPU01() {
		// CPU思考回路の設定
		// 判断の優先度順に設定する。
		this.setIdeaSpacialList(new IdeaSpeRecoverLifePoint20());
		this.setIdeaSpacialList(new IdeaSpeUpAttack10());
	}
	
	

}
