package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class BattleDisplay extends BaseActivity {
	
	private BattleActivity activity = null;
	
	private float posX = 0.0f;
	private float posY = 0.0f;
	private float bBLayoutPosY = 0.0f;
	
	/**
	 * コンストラクタ
	 * @param activity 対戦画面のアクティビティ
	 */
	public BattleDisplay(BattleActivity activity) {
		this.activity = activity;
	}
	
	public float getPosX() {
		return posX;
	}



	public void setPosX() {
		WindowManager wm = (WindowManager)this.activity.getSystemService(WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		if(disp.getWidth() != 0){
			// px値をhdip値へ変換
			this.posX = disp.getWidth() / tmpDensity;
		}
	}



	public float getPosY() {
		return posY;
	}



	public void setPosY() {
		// ディスプレイのインスタンス生成
		WindowManager wm = (WindowManager)this.activity.getSystemService(WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		if(disp.getHeight() != 0){
			// px値をhdip値へ変換
			this.posY = disp.getHeight() / tmpDensity;
		}
		this.setBBLayoutPosY(this.posY);
	}



	public void setBBLayoutPosY(float posY){
		//値が全て0.0fなのは何故？
		Float spe_card1 = (float)this.activity.findViewById(R.id.spe_card1).getHeight();
		Float enemyName = (float)this.activity.findViewById(R.id.battle_enemyName).getHeight();
		Float enemyLp = (float)this.activity.findViewById(R.id.battle_enemyLp).getHeight();
		Float timelimit = (float)this.activity.findViewById(R.id.battle_timelimit).getHeight();
		Rect rect= new Rect();
		Window window= this.activity.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rect);
		Integer statusBarHeight= rect.top;
		
		Log.d("VVVVVVVVVVVVVV", spe_card1.toString());
		Log.d("VVVVVVVVVVVVVV", enemyName.toString());
		Log.d("VVVVVVVVVVVVVV", enemyLp.toString());
		Log.d("VVVVVVVVVVVVVV", timelimit.toString());
		Log.d("VVVVVVVVVVVVVV", statusBarHeight.toString());
		
		
		if(posY > 0.0f){
			// 相手名　相手ＬＰ　自分ＬＰ　上バー分の値を引く
			posY = posY - 20.0f - 20.0f - 20.0f - 35.0f;
		}
		this.bBLayoutPosY = posY;
	}
	
	public float getBBLayoutPosY(){
		return this.bBLayoutPosY;
	}
	
}
