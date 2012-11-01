package com.ict.apps.bobb.bobbactivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class BattleCardsSet extends View {

	public BattleCardsSet(Context context) {
		super(context);
	}
	
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		Resources res = getResources();
		
		Bitmap bitmap1 = BitmapFactory.decodeResource(res, R.drawable.cards);
		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, 100, 150, false);
		
		Paint paint = new Paint();
		
		canvas.drawBitmap(bitmap2, 200, 150, paint);
		
		Bitmap bitmap3 = BitmapFactory.decodeResource(res, R.drawable.cards);
		Bitmap bitmap4 = Bitmap.createScaledBitmap(bitmap3, 100, 150, false);
		
//		Paint paint = new Paint();
		
		canvas.drawBitmap(bitmap4, 200, 400, paint);
	}

}
