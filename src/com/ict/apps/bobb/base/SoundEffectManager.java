package com.ict.apps.bobb.base;

import java.util.HashMap;

import com.ict.apps.bobb.bobbactivity.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class SoundEffectManager {
	
	private static SoundEffectManager instance = null;

	private Context context = null;

	// サウンドをプールするインスタンス
	private SoundPool sp;

	// 最大効果音数
	private int maxPoolNum = 0;

	private HashMap<Integer, Integer> spNumMap = new HashMap<Integer, Integer>();

	public SoundEffectManager (Context context) {
		this.context = context;
		this.maxPoolNum = 12;
		this.createSoundPool();
	}

	public static SoundEffectManager getInstance(Context context) {
		if (SoundEffectManager.instance == null) {
			SoundEffectManager.instance = new SoundEffectManager(context);
			
			SoundEffectManager.instance.loadEffect(R.raw.breed_create1);
			SoundEffectManager.instance.loadEffect(R.raw.breed_create2);
			SoundEffectManager.instance.loadEffect(R.raw.deal_card);
			SoundEffectManager.instance.loadEffect(R.raw.card_open);
			SoundEffectManager.instance.loadEffect(R.raw.card_set);
			SoundEffectManager.instance.loadEffect(R.raw.push);
			SoundEffectManager.instance.loadEffect(R.raw.attribute);
			SoundEffectManager.instance.loadEffect(R.raw.effect);
			SoundEffectManager.instance.loadEffect(R.raw.crash);
			SoundEffectManager.instance.loadEffect(R.raw.win);
			SoundEffectManager.instance.loadEffect(R.raw.lose);
			SoundEffectManager.instance.loadEffect(R.raw.draw);

		}
		return SoundEffectManager.instance;
	}
	
	/**
	 * 
	 */
	private void createSoundPool() {
		this.sp = new SoundPool( this.maxPoolNum, AudioManager.STREAM_MUSIC, 0);

	}
	
	
	public void loadEffect(int resId) {

		/*
		  第一引数にContext、
		  第二引数にリソース、
		  第三引数に優先度を渡します。
		  第三引数は現在は使用されてなくて、整合性のために１を渡さなければいけないみたいです。
		  そして load メソッドはSoundPoolクラスを操作するのに重要な引数を返します。
		  プールされた曲を指定するためのIDです。
		  再生やメモリ解放などのときに使用するので保持しておく必要があります。
		*/
		
		this.spNumMap.put(resId, this.sp.load( this.context , resId, 1));

//		this.sp.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//			@Override
//			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//				if (0 == status) {
//					// 
//					Toast.makeText(getApplicationContext(), "LoadComplete",
//							Toast.LENGTH_LONG).show();
//				}
//			}
//		});
	}
	
	/**
	 * プールしている効果音を再生する
	 * @param resId
	 */
	public void play(int resId) {
		
		// 引数の左から、ID、左スピーカー音量、右スピーカー音量、優先度、ループ回数、再生速度です。
		this.sp.play( this.spNumMap.get(resId) , 1.0F , 1.0F , 0 , 0 , 1.0F );
		
	}


}
