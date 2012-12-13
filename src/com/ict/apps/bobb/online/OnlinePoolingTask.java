package com.ict.apps.bobb.online;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * オンライン対戦のポーリング通信要求
 */
public class OnlinePoolingTask extends AsyncTask<OnlineQuery, Integer, Integer> {

	private final String TAG = "OnlinePoolingTask";
	private ProgressDialog dialog = null;
	private Context context = null;	
	
	private OnlineQuery query = null;
	
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
		this.dialog.setMessage("通信中です\nしばらくおまちください...");
		this.dialog.show();
	}

	@Override
	protected Integer doInBackground(OnlineQuery... params) {
		Log.d(TAG, "doInBackground - ");
		
		Integer retValue = -1;
		if (params[0] != null) {
			this.query = params[0];
			try {
				
				// 設定した秒数ループを実施する（デフォルト30秒）
				int loopCnt = this.query.getPoolingCount();
				for(int i = 0; i < loopCnt; i++) {
					String response = OnlineConnection.post(this.query);
					Log.d(TAG, "doInBackground - response = " + response);
					if (response != null
						&&(!this.analyzeResponse(response))
						&& (this.query.isPoolingFinish(response))) {
						
						// クエリ-インスタンスにレスポンスデータを設定
						this.query.setResponse(response);
//						this.query.execAfterReceiveingAction(this.context);
						retValue = 0;
						break;
					}
					Thread.sleep(1000);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
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

		if (result != 0) {
			Toast.makeText(this.context, "通信タイムアウト", Toast.LENGTH_SHORT).show();
		}
		
		// Query固有の受信後処理を実施する
		this.query.execAfterReceiveingAction(this.context, result);
		
		
		// ブロードキャスト
		OnlineUtil.completeQery(this.context, result == 0 ? "success" : "error");
		
		// データクリア
		this.query = null;
		
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
	
	/**
	 * レスポンスデータ解析
	 * @param data
	 * @return
	 */
	private boolean analyzeResponse(String data){
		// 返却データが[]の場合、条件に合致したレコード無し
		return "[]".equals(data);
	}

}
