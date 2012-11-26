package com.ict.apps.bobb.online;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

/**
 * オンライン対戦のシングル通信要求
 */
public class OnlineOneTimeTask extends AsyncTask<OnlineQuery, Integer, Integer> {

	private final String TAG = "OnlineOneTimeTask";
	private Context context = null;	
	
	private OnlineQuery query = null;
	
	/**
	 * コンストラクタ
	 * @param context
	 */
	public OnlineOneTimeTask(Context context) {
		this.context = context;
	}

	@Override
	protected Integer doInBackground(OnlineQuery... params) {
		Log.d(TAG, "doInBackground - ");
		
		Integer result = -1;
		if (params[0] != null) {
			this.query = params[0];
			try {
				String response = OnlineConnection.post(this.query);
				Log.d(TAG, "doInBackground - response = " + response);
				
				// クエリ-インスタンスにレスポンスデータを設定
				this.query.setResponse(response);
				this.query.execAfterReceiveingAction(this.context);
				
				result = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		// doInBackgroundが終了した場合にその復帰値を引数として受ける。
		Log.d(TAG, "onPostExecute - " + result);
//		this.query.execAfterReceiveingAction(this.context);
		
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// doInBackgroundが途中でpublishProgressを呼ぶとその引数を受ける。
		Log.d(TAG, "onProgressUpdate - " + values[0]);
	}

	@Override
	protected void onCancelled() {
		// キャンセルthis.cancelが呼ばれた状態でdoInBackgroundを終了した場合に呼ばれる
		Log.d(TAG, "onCancelled");
	}

}
