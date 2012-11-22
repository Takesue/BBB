package com.ict.apps.bobb.online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

/**
 * HTTPでのオンライン接続用クラス
 * Get機能／Post機能
 */
public class OnlineConnection {

	// ログ出力用タグ
	private static final String TAG = "OnlineConnection";

	/**
	 * サーバへのPOSTリクエスト
	 * 
	 * @param endpoint POSTするアドレス
	 * @param params リクエストのパラメタ
	 */
	public static String post(String endpoint, Map<String, String> params) throws IOException {
		
		String responseString = null;
		
		URL url;
		
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		
		// 引数のparamsを元手にPOSTリクエストのボディ部を成形
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		Log.v(TAG, "Posting '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			// リクエスト実行
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			
			// レスポンス情報を受ける
			int status = conn.getResponseCode();
			if (status == 200) {
				responseString = OnlineConnection.parseResult(conn.getInputStream());
			}
			else {
				throw new IOException("Post failed with error code " + status);
			}
			
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		return responseString;
	}
	
	/**
	 * 受信結果（レスポンス）を解析
	 * @param is
	 * @throws IOException
	 */
	private static String parseResult(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// 先頭行が空行以外の場合は、エラー
		boolean error_responce = false;
		String s = "";
		String out = "";

		// 一行ずつ読み込み
		while (null != (s = br.readLine())) {
			if (0 == out.length()) {
				// 最初の行はそのまま
				out += s;
			} else {
				out += "\n" + s;
			}
		}

		if (error_responce) {
			throw new RuntimeException("failuer of analyze: " + out);
		}
		return out;
	}

	/**
	 * POSTリクエスト
	 * @param query OnlineQuery
	 * @throws IOException
	 */
	public static String post(OnlineQuery query) throws IOException {
		return OnlineConnection.post(query.getServerURL(), query.getParam());
	}

}
