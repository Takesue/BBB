package com.ict.apps.bobb.battle;

import com.ict.apps.bobb.bobbactivity.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BattleToast extends Toast {

	private Activity activity = null;
	private View layout = null;
	
	/**
	 * コンテキスト
	 * @param context
	 */
	public BattleToast(Context context) {
		super(context);
		this.activity = (Activity)context;
		this.setDefaultVew();
	}
	
	
	/**
	 * Viewを取得してToastに設定し、デフォルト値を設定する。
	 */
	private void setDefaultVew(){
		
		LayoutInflater inflater = this.activity.getLayoutInflater();
		this.layout = inflater.inflate(R.layout.battle_toast_layout_root, (ViewGroup) this.activity.findViewById(R.id.battle_toast_layout_root));

		this.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		this.setDuration(Toast.LENGTH_LONG);

		this.setView(layout);
	}
	
	/**
	 * テキストを設定
	 * @param message
	 */
	public void setText(String message) {
		TextView text = (TextView) layout.findViewById(R.id.battleToastText);
		text.setText(message);
	}
	

	/**
	 * テキスト背景を設定
	 * @param message
	 */
	public void setTextBackground(int resid) {
		TextView text = (TextView) layout.findViewById(R.id.battleToastText);
		text.setBackgroundResource(resid);
	}

}
