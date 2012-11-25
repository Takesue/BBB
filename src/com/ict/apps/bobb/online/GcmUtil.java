package com.ict.apps.bobb.online;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMRegistrar;

public class GcmUtil {
	
	
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

	
	public static final String POPUP_MESSAGE_ACTION = "com.ict.apps.bobb.online.POPUP_MESSAGE";
	public static final String EXTRA_MESSAGE = "message";

	/**
	 * 
	 * <p>
	 * @param context 
	 * @param message
	 */
	public static void popupMessage(Context context, String message) {
		Intent intent = new Intent(POPUP_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

}
