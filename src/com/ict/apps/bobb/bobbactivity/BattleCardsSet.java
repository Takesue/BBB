package com.ict.apps.bobb.bobbactivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class BattleCardsSet extends Activity {

	// カードの表示部品（card.xml）
	private View viewCard = null;

//	public BattleCardsSet(Context context) {
//		super(context);
//	}
	
	protected void onDraw(Canvas canvas){
//		super.onDraw(canvas);
		
		Resources res = getResources();
		
		Bitmap rivalcard1 = BitmapFactory.decodeResource(res, R.drawable.cards);
		Bitmap rivalcard2 = Bitmap.createScaledBitmap(rivalcard1, 100, 150, false);
		
		Paint paint = new Paint();
		
		canvas.drawBitmap(rivalcard2, 200, 150, paint);
		
		this.viewCard = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
						R.layout.my_cards, null);
		
	}

}
