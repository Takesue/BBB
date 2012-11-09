package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import com.ict.apps.bobb.battle.cpu.CPU01;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;

import android.os.Handler;
import android.view.View;

public class BattleSceneBattleAnimation implements BattleScene {

	// 対戦画面アクティビティ
	private BattleActivity activity = null;


	/**
	 * コンストラクタ
	 * @param activity 対戦画面のアクティビティ
	 */
	public BattleSceneBattleAnimation(BattleActivity activity) {
		this.activity = activity;
	}

	@Override
	public void init() {
		// 画面に必要な情報を設定
		
		this.getEnemySelectCards();

	}

	@Override
	public void moveCard(BattleCardView view, int action) {
	}

	@Override
	public void finish() {
		// 表示しているビューを全て削除する
		this.activity.baseLayout.removeAllViews();
	}

	@Override
	public void onLongClickCard(BattleCardView view) {
	}

	@Override
	public void actionUpCard(BattleCardView view) {
	}

	/**
	 * 相手のカードを取得
	 */
	private ArrayList<BattleCardView> getEnemySelectCards() {
		return this.activity.cpu.getSelectCard(this.activity.myInfo, this.activity.enemyInfo);
	}
	
	/**
	 * 相手の特殊カードを取得
	 */
	private ArrayList<BattleCardView> getEnemySelectSpecialCards() {
		return this.activity.cpu.getSelectSpacialCard(this.activity.myInfo, this.activity.enemyInfo);
	}


	// ハンドラー取得
	private Handler mHandler = new Handler();

	/**
	 * シーン変更（別スレッド呼び出し用）
	 */
	private void callChangeNexrScene() {
		
		// 別スレッドから呼ぶので、ハンドラーで実装する
		this.mHandler.post(new Runnable() {
			public void run() {
				activity.changeNextScene();
			}
		});
	}

}
