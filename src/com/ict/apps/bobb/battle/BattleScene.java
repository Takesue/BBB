package com.ict.apps.bobb.battle;

import android.view.View;

public interface BattleScene {

	/**
	 *  初期化メソッド
	 */
	public void init();
	
	/**
	 * カードオブジェクトがタッチされた場合
	 * @param view
	 * @param action 
	 */
	public void moveCard(View view, int action);
	
	/**
	 *  終了メソッド
	 */
	public void finish();
	
}
