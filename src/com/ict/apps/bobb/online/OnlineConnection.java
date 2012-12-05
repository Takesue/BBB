package com.ict.apps.bobb.online;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import android.widget.Toast;

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
	public static synchronized String post(String endpoint, Map<String, String> params) throws IOException {
		
		String responseString = null;
		
		// URL
		URI url = null;
		try {
			url = new URI(endpoint);
			Log.d("OnlineConnection", "URL:" + endpoint);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// POSTパラメータ付きでPOSTリクエストを構築
		HttpPost request = new HttpPost(url);

		List<NameValuePair> post_params = new ArrayList<NameValuePair>();
		
		// 引数のparamsを元手にPOSTリクエストのボディ部を成形
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			post_params.add(new BasicNameValuePair(param.getKey(), param.getValue()));
		}
		
		// POSTリクエストを実行
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			// -----[POST送信]
			request.setEntity(new UrlEncodedFormEntity(post_params, "UTF-8"));
			
			HttpResponse response = httpClient.execute(request);
			
			// -----[サーバーからの応答を取得]
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
				
			} else {
//				Toast.makeText(this, "[error]: " + response.getStatusLine(),
//						Toast.LENGTH_LONG).show();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseString;
	}
	
//	/**
//	 * 受信結果（レスポンス）を解析
//	 * @param is
//	 * @throws IOException
//	 */
//	private static String parseResult(InputStream is) throws IOException {
//		String CHARSET = "UTF-8";  // "UTF-8", "EUC-JP"
//		BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET));
//
//		// 先頭行が空行以外の場合は、エラー
//		boolean error_responce = false;
//		String s = "";
//		String out = "";
//
//		// 一行ずつ読み込み
//		while (null != (s = br.readLine())) {
//			if (0 == out.length()) {
//				// 最初の行はそのまま
//				out += s;
//			} else {
//				out += "\n" + s;
//			}
//		}
//
//		if (error_responce) {
//			throw new RuntimeException("failuer of analyze: " + out);
//		}
//		return out;
//	}

	/**
	 * POSTリクエスト
	 * @param query OnlineQuery
	 * @throws IOException
	 */
	public static String post(OnlineQuery query) throws IOException {
		return OnlineConnection.post(query.getServerURL(), query.getParam());
	}

}
