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
//	private ProgressDialog dialog = null;
	private Context context = null;	
	
	/**
	 * コンストラクタ
	 * @param context
	 */
	public OnlineOneTimeTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		Log.d(TAG, "onPreExecute");
//		this.dialog = new ProgressDialog(context);
//		this.dialog.setIndeterminate(true);
//		this.dialog.setMessage("通信処理中です。\nしばらくおまちください...");
//		this.dialog.setTitle("Please wait");
//		this.dialog.setMessage("Loading data...");
//		this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//		this.dialog.setCancelable(true);
//		this.dialog.setOnCancelListener(this);
//		this.dialog.setMax(100);
//		this.dialog.setProgress(0);
//		this.dialog.show();
		
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
//		this.dialog.dismiss();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// doInBackgroundが途中でpublishProgressを呼ぶとその引数を受ける。
		Log.d(TAG, "onProgressUpdate - " + values[0]);
//		this.dialog.setProgress(values[0]);
	}

	@Override
	protected void onCancelled() {
		// キャンセルthis.cancelが呼ばれた状態でdoInBackgroundを終了した場合に呼ばれる
		Log.d(TAG, "onCancelled");
//		this.dialog.dismiss();
	}

//	@Override
//	public void onCancel(DialogInterface dialog) {
//		// ダイアログでキャンセルを選択された場合
//		Log.d(TAG, "Dialog onCancell... calling cancel(true)");
//		this.cancel(true);
//	}

}
