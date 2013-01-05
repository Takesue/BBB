package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;

import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class BattleDisplay extends BaseActivity {
	
	private float posX = 0.0f;
	private float posY = 0.0f;
	private float bBLayoutPosY = 0.0f;
	
	
	public float getPosX() {
		return posX;
	}



	public void setPosX(WindowManager wm) {
		Display disp = wm.getDefaultDisplay();
		if(disp.getWidth() != 0){
			// px値をhdip値へ変換
			this.posX = disp.getWidth() / 3 * 2;
		}
//		this.posX = posX;
	}



	public float getPosY() {
		return posY;
	}



	public void setPosY(WindowManager wm) {
		// ディスプレイのインスタンス生成
		Display disp = wm.getDefaultDisplay();
		if(disp.getHeight() != 0){
			// px値をhdip値へ変換
			this.posY = disp.getHeight() / 3 * 2;
		}
		this.setBBLayoutPosY(this.posY);
	}



	public float getBaseLayoutPosY(float posY){
		if(posY > 0.0f){
			// 相手名　相手ＬＰ　自分ＬＰ　上バー分の値を引く
			posY = posY - 20.0f - 20.0f - 20.0f - 35.0f;
		}
		return posY;
	}
	
	public void setBBLayoutPosY(float posY){
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
