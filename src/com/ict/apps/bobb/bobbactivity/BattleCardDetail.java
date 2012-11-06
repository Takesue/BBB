package com.ict.apps.bobb.bobbactivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleCardDetail extends RelativeLayout {

	public BattleCardDetail(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BattleCardDetail(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BattleCardDetail(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		// ここでtrueを返せば親ViewのonTouchEvent
		// ここでfalseを返せば子ViewのonClickやらonLongClickやら
//		return false;
		return super.onInterceptTouchEvent(ev);
	}
	
}
