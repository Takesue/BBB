package com.ict.apps.bobb.online;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * オンライン対戦のポーリング通信要求
 */
public class OnlinePoolingTask extends AsyncTask<OnlineQuery, Integer, Integer> {

	private final String TAG = "OnlinePoolingTask";
	private ProgressDialog dialog = null;
	private Context context = null;	
	
	/**
	 * コンストラクタ
	 * @param context
	 */
	public OnlinePoolingTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		Log.d(TAG, "onPreExecute");
		this.dialog = new ProgressDialog(context);
		this.dialog.setIndeterminate(true);
		this.dialog.setMessage("対戦相手の応答待ちです。\nしばらくおまちください...");
		this.dialog.show();
	}

	@Override
	protected Integer doInBackground(OnlineQuery... params) {
		Log.d(TAG, "doInBackground - ");
		
		Integer retValue = -1;
		if (params[0] != null) {
			OnlineQuery query = params[0];
			try {
				String response = OnlineConnection.post(query);
				Log.d(TAG, "doInBackground - response = " + response);
				
				// クエリ-インスタンスにレスポンスデータを設定
				query.setResponse(response);
				
				retValue = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retValue;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		// doInBackgroundが終了した場合にその復帰値を引数として受ける。
		Log.d(TAG, "onPostExecute - " + result);
		this.dialog.dismiss();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// doInBackgroundが途中でpublishProgressを呼ぶとその引数を受ける。
		Log.d(TAG, "onProgressUpdate - " + values[0]);
		this.dialog.setProgress(values[0]);
	}

	@Override
	protected void onCancelled() {
		// キャンセルthis.cancelが呼ばれた状態でdoInBackgroundを終了した場合に呼ばれる
		Log.d(TAG, "onCancelled");
		this.dialog.dismiss();
	}

}
