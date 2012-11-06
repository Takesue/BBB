package com.ict.apps.bobb.base;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * BGM管理クラス (シングルトンクラスです)
 */
public class BgmManager {

	private static BgmManager instance = null;

	// 現在再生している曲
	private MediaPlayer player;

	// 現在再生している曲のResId
	private int currentPlayBgm;

	// 最初に取得した時のコンテキスト恐らく、開始画面のコンテキスト
	private static Context context;

	private BgmManager() {
		currentPlayBgm = -1;
	}

	public synchronized static BgmManager get(Context context) {
		
		if(BgmManager.instance == null) {
			BgmManager.instance = new BgmManager();
			BgmManager.context = context;
		}
		return BgmManager.instance;
	}

	public static void release() {
		if(BgmManager.instance != null) {
			BgmManager.instance.stop();
			BgmManager.instance = null;
		}
	}
	
	/**
	 * 再生　
	 * @param resid
	 */
	public void play(int id) {
		if (id == this.currentPlayBgm) { // 現在再生しているので何もしない
			return;
		} else {
			this.currentPlayBgm = id;
			
			// 現在の曲を止める。
			this.stop();
			
			if (this.player == null) {
				this.player = MediaPlayer.create(BgmManager.context, this.currentPlayBgm);
				this.player.setLooping(true);
				this.player.start();
			}
		}
	}

	public void stop() { // 現在の曲を止める
		if (this.player != null) {
			this.player.stop();
			this.player.release();
			this.player = null;
			this.currentPlayBgm = -1;
		}
	}
	
	public boolean matchPlayContext(Context context) {
		return BgmManager.context.equals(context);
	}
	
	
	
}
