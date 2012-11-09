package com.ict.apps.bobb.battle;

import com.ict.apps.bobb.bobbactivity.BattleCardView;


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
	public void moveCard(BattleCardView view, int action);
	
	/**
	 *  終了メソッド
	 */
	public void finish();
	
	/**
	 * カードオブジェクトが長押しされた場合に呼ばれる
	 */
	public void onLongClickCard(BattleCardView view);

	/**
	 * カードオブジェクトから手を離す場合に呼ばれる
	 */
	public void actionUpCard(BattleCardView view);
	
}
