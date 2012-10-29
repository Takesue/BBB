package com.ict.apps.bobb.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ステータス情報
 * @author take
 *
 */
public class StatusInfo {
	
	// SharedPref名
	private static final String NAME_STATUS_INFO = "StatusInfo";
	// ユーザ名
	private static final String INFO_USER_NAME = "user_name";
	// レベル
	private static final String INFO_USER_LEVEL = "level";
	// ライフポイント
	private static final String INFO_LIFE_POINT = "life_point";
	// 対戦回数
	private static final String INFO_BUTTOLE_COUNT = "buttle_count";
	// 経験値
	private static final String INFO_EXP = "exp";
	

	/**
	 * ユーザ名取得
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		return pref.getString(StatusInfo.INFO_USER_NAME, "");
	}
	
	/**
	 * ユーザ名設定
	 * @param context
	 * @return
	 */
	public static void setUserName(Context context, String name) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(StatusInfo.INFO_USER_NAME, name);
		editor.commit();
	}
	
	/**
	 * レベル取得
	 * @param context
	 * @return
	 */
	public static int getLevel(Context context) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		return pref.getInt(StatusInfo.INFO_USER_LEVEL, 0);
	}
	
	/**
	 * レベル設定
	 * @param context
	 * @return
	 */
	public static void setLevel(Context context, int level) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(StatusInfo.INFO_USER_LEVEL, level);
		editor.commit();
	}
	
	/**
	 * ライフポイント取得
	 * @param context
	 * @return
	 */
	public static int getLP(Context context) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		return pref.getInt(StatusInfo.INFO_LIFE_POINT, 0);
	}
	
	/**
	 * ライフポイント設定
	 * @param context
	 * @return
	 */
	public static void setLP(Context context, int lp) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(StatusInfo.INFO_LIFE_POINT, lp);
		editor.commit();
	}
	
	/**
	 * 対戦回数取得
	 * @param context
	 * @return
	 */
	public static int getButtleCount(Context context) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		return pref.getInt(StatusInfo.INFO_BUTTOLE_COUNT, 0);
	}
	
	/**
	 * 対戦回数設定
	 * @param context
	 * @return
	 */
	public static void setButtleCount(Context context, int count) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(StatusInfo.INFO_BUTTOLE_COUNT, count);
		editor.commit();
	}

	/**
	 * 経験値取得
	 * @param context
	 * @return
	 */
	public static int getEXP(Context context) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		return pref.getInt(StatusInfo.INFO_EXP, 0);
	}
	
	/**
	 * 経験値設定
	 * @param context
	 * @return
	 */
	public static void setEXP(Context context, int exp) {
		SharedPreferences pref = StatusInfo.openPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(StatusInfo.INFO_EXP, exp);
		editor.commit();
	}

	
	/**
	 * SharedPreferencesへアクセス
	 * @param context
	 * @return
	 */
	private static SharedPreferences openPreferences(Context context) {
		return context.getSharedPreferences(StatusInfo.NAME_STATUS_INFO, Context.MODE_PRIVATE);
	}

}
