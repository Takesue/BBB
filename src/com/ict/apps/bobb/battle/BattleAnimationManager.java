package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import android.os.Handler;

public class BattleAnimationManager {
	
	// アニメーション用Runnnable保持Array
	private ArrayList<Runnable> runnableList = new ArrayList<Runnable>();
	private ArrayList<Long> durationList = new ArrayList<Long>();
	
	// ハンドラー
	private Handler mHandler = new Handler();
	
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
