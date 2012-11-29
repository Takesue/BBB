package com.ict.apps.bobb.online;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMRegistrar;

public class OnlineUtil {
	
	
	/**
	 * 端末をGCMへ登録する
	 * @param context
	 */
	public static void registerDevice(Context context) {
		
		// 端末がGCM対応しているかチェック
		GCMRegistrar.checkDevice(context);
		// マニュフェストファイルがGCM対応しているかチェック
		GCMRegistrar.checkManifest(context);

		// 登録している場合、
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
			// Automatically registers application on startup.
			GCMRegistrar.register(context, OnlineQuery.SENDER_ID);
		}
	}
	
	
	/**
	 * 端末をGCMへ登録解除する
	 * @param context
	 */
	public static void unregisterDevice(Context context) {
		
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (!regId.equals("")) {
			GCMRegistrar.unregister(context);
		}
	}

	// 対戦相手要求　ポップアップ
	public static final String POPUP_MESSAGE_ACTION = "com.ict.apps.bobb.online.POPUP_MESSAGE";

	// クエリ-完了通知
	public static final String QERY_COMPLETE_ACTION = "com.ict.apps.bobb.online.QUERY_COMPLETE";
	
	// INTENTの引数（メッセージ）のキーNAME
	public static final String EXTRA_MESSAGE = "message";


	/**
	 * PUSHされた対戦要求をブロードキャストで通知
	 * <p>
	 * @param context 
	 * @param message
	 */
	public static void popupMessage(Context context, String message) {
		Intent intent = new Intent(POPUP_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	/**
	 * 発行したクエリ-が完了したことをブロードキャストで通知
	 * <p>
	 * @param context 
	 * @param message
	 */
	public static void completeQery(Context context, String message) {
		Intent intent = new Intent(QERY_COMPLETE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

}
