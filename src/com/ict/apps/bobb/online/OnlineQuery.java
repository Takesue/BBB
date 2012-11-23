package com.ict.apps.bobb.online;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public abstract class OnlineQuery {

	/**
	 * Base URL of the Demo Server
	 */
	public static final String SERVER_URL = "http://project-bobb-webapl.herokuapp.com/bobb_req";

	/**
	 * Google API project id registered to use GCM.
	 */
	public static final String SENDER_ID = "1041399877162";
	
	// リクエストの結果Json形式
	private JSONArray response = null;
	
	/**
	 * リクエストURLを返却
	 * @return
	 *         
	 */
	public abstract String getServerURL();
	
	/**
	 * Postするパラメタを返却
	 * @return
	 *         Post時のパラメタを返却
	 */
	public abstract Map<String, String> getParam();
	
	/**
	 * リクエストの結果文字列を設定
	 * @param response　NULLだとNullPointer発生
	 */
	public void setResponse(String response) {
		try {
			// 文字列をJson形式に取り込んで保持する
			this.response = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * Json形式の結果を解析して、JsonObjectに格納して返却する
//	 * 各々クラスによって異なる
//	 */
//	protected void parseJsonFormat(String...colnames ) {
//		
//		try {
//			int cnt = this.response.length();
//			for (int i = 0; i < cnt; i++) {
//				JSONObject responseLine = this.response.getJSONObject(i);
//				
//				for (int j = 0; j < colnames.length; j++) {
//					String aa = responseLine.getString(colnames[j]);
//				}
//			}
//		} catch (JSONException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//
//	}

}
