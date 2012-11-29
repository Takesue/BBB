package com.ict.apps.bobb.base;


import com.google.android.gcm.GCMRegistrar;
import com.ict.apps.bobb.battle.BattleToast;
import com.ict.apps.bobb.bobbactivity.MainMenuActivity;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.bobbactivity.RuleActivity;
import com.ict.apps.bobb.online.OnlineUtil;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Activityを継称する画面クラスの基底クラス
 *
 */
public abstract class BaseActivity extends Activity {
	

	// BGM管理インスタンス
	private static BgmManager bgm = null;

	// 効果音管理インスタンス
	private static SoundEffectManager effect = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 効果音をロードしておく
		this.loadEffect();
		
//		// メモリ残量表示
//		this.MemoryDisplay();
		
		// ブロードキャストレシーバの登録
		this.registerReceiver(this.mHandleMessageReceiver, new IntentFilter(OnlineUtil.POPUP_MESSAGE_ACTION));
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		this.startBgm();
	}

	/**
	 * BGM再生
	 */
	protected void startBgm() {
		
		if (BaseActivity.bgm == null) {
			BaseActivity.bgm = BgmManager.get(this);
		}

		BaseActivity.bgm.play(R.raw.breed_bgm);
	}
	
	/**
	 * BGM停止
	 */
	protected void stopBgm() {
		if (BaseActivity.bgm != null) {
			BaseActivity.bgm.stop();
		}
	}
	
	/**
	 * 効果音リソースをあらかじめロードしておく
	 */
	private void loadEffect() {
		
		// 効果音をロードしておく
		BaseActivity.effect = SoundEffectManager.getInstance(this);
		BaseActivity.effect.loadEffect(R.raw.breed_create1);
		BaseActivity.effect.loadEffect(R.raw.breed_create2);
	}
	
	/**
	 * 指定したリソースIDの効果音を再生する
	 * @param resId
	 */
	protected void playEffect(int resId) {
		BaseActivity.effect.play(resId);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
//		Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
		
		if (BaseActivity.bgm.matchPlayContext(this)) {
			// 回収処理
			this.stopBgm();
			BgmManager.release();
			BaseActivity.bgm = null;
//			Toast.makeText(getApplicationContext(), "releace BGM instance", Toast.LENGTH_SHORT).show();
		}
		
		// 解放できていなかった場合
		this.running = false;
		th = null;
		
		this.unregisterReceiver(mHandleMessageReceiver);
		
	}

	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		
		if (BaseActivity.bgm != null) {
			this.stopBgm();
//			BgmManager.release();
//			BaseActivity.bgm = null;
		}
//		Toast.makeText(getApplicationContext(), "onUserLeaveHint", Toast.LENGTH_SHORT).show();
		
		// 解放できていなかった場合
		this.running = false;
		th = null;


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bobb_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.menu_top:
			// TOPへ戻る
			// いままでの画面スタックは削除してトップ画面に戻る
			Intent intent = new Intent(this, MainMenuActivity.class);  
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
				Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.startActivity(intent); 

			break;
		case R.id.menu_rule:
			// ルール説明
			Toast.makeText(this, "ruleだよ", Toast.LENGTH_SHORT).show();
			this.callRuleView();
			break;
		 }
		
		return true;
	}
	
	/**
	 * 説明画面を呼び出す
	 */
	public void callRuleView() {
		Intent intent = new Intent(this, RuleActivity.class);
		this.startActivity(intent); 
	}
	
	
	
	// メモリ表示再描画用のスレッド
	private static Thread th = null;
	// メモリ表示再描画用のハンドラ
	private Handler mHandler = new Handler();
	// メモリ表示再描画用スレッドの実行フラグ
	private boolean running = true;

	public void MemoryDisplay() {
//		final TextView titleText = (TextView) this.findViewById(android.R.id.title);

		final Context con = this;
		// 初回に一回だけ実行
		if (th == null) {
			running = true;
			th = new Thread(new Runnable() {
				public void run() {
					while(running) {
						// 再描画のキューをセット
						mHandler.post(new Runnable() {
							public void run() {
//								titleText.setText("AvaMem:" + cmnUtil.getAvailMemorySize());
								Toast.makeText(con, "AvaMem:" + getAvailMemorySize() , 100).show();
							}
						});

						// スリープ
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			
			Log.d("★", "Thread Start ");
			th.start();
		}
	}

	
	
	/**
	 * 	空きメモリ量取得
	 * @return 使用可能メモリ量（MB）
	 */
	public long getAvailMemorySize() {
		long size = 0;

		ActivityManager activityManager = ((ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE));

		//メモリ情報の取得
		ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		size = memoryInfo.availMem;		// 使用可能メモリ
		
		return size/1000000;
	}

	
	// ブロードキャストメッセージ
	// 対戦相手通知があったことを受ける
	private final BaseActivity content = this;
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(
					OnlineUtil.EXTRA_MESSAGE);
			BattleToast toast = new BattleToast(content);
			toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
			toast.setText(newMessage);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.show();
		}
	};
	
	
}
