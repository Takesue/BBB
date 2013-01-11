package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import com.ict.apps.bobb.bobbactivity.BattleActivity;

import android.os.Handler;

public class BattleAnimationManager {
	
	// アニメーション用Runnnable保持Array
	private ArrayList<Runnable> runnableList = new ArrayList<Runnable>();
	private ArrayList<Long> durationList = new ArrayList<Long>();
	
	// ハンドラー
	private Handler mHandler = new Handler();
	
	// 対戦画面アクティビティ
	private BattleActivity activity = null;
	
	/**
	 * コンストラクタ
	 * @param activity
	 */
	public BattleAnimationManager(BattleActivity activity) {
		this.activity = activity;
	}
	
	/**
	 * アニメーション追加
	 * @param r
	 * @param duration
	 */
	public void add(Runnable r, long duration) {
		
		if (r != null) {
			this.runnableList.add(r);
			this.durationList.add(duration);
		}
	}

	/**
	 * アニメーションを複数実行する
	 * 別スレッドで実行して回す
	 */
	private void execAnimations() {
		
		try {
			int length = this.runnableList.size();
			for (int i = 0; i < length; i++) {
				
				// ユーザ操作等で既に対戦が終了しているかどうか確認
				if (this.activity.bm.isBattleFifnishFlag()){
					// アニメーションを中断する
					return;
				}
				
				// アニメーションを実行する
				mHandler.post(this.runnableList.get(i));
				
				// duration分待って次を実行する
				Thread.sleep(this.durationList.get(i));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * アニメーション開始
	 */
	public void startAnimations() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				execAnimations();
			}
		}).start();
	}
	
}
