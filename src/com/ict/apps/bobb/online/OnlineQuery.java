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
	
	/**
	 *  レスポンスの取得レコード数取得
	 * @return レコード数
	 */
	public int getResponseRecordCount() {
		return this.response.length();
	}
	
	/**
	 * 取得したデータから取得対象の情報の行数、カラム名を指定して該当データを取得する。
	 * @param recnum　行数
	 * @param colname　カラム名
	 * @return　データ　該当データなしの場合NULLを返却
	 */
	public String getResponseData(int recnum , String colname ) {
		String responseData = null;
		try {
			responseData = this.response.getJSONObject(recnum).getString(colname);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseData;
	}
	
	

}
