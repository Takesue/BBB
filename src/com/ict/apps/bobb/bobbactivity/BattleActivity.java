package com.ict.apps.bobb.bobbactivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleActivity extends Activity implements OnTouchListener {

	int cardCount = 30;
	int maxTime = 5;
	
	private BattleActivity bAct;

	private BattleCardDetail vgroup;

	// カードの表示部品（card.xml）
	private View myViewCard[]    = new View[this.cardCount];
	private View rivalViewCard[] = new View[this.cardCount];

	// カード詳細表示部品(carddetail.xml)
	private View myViewCardDerail = null;
	
	// カードTOUCH時山札か、配布済みか判断
	boolean cardEvent = false;
	
	// カードが移動するアニメーションを作ってみる　利用したのは、ハンドラーの遅延でスレッド仕込むやつ。
	
	private boolean status = false;
	private boolean status2 = false;
	private boolean status3 = false;
	private boolean status4 = false;
	private boolean status5 = false;
	
	// ハンドラーを取得
	private Handler mHandler = new Handler();
	
	// 移動の開始位置と終了位置を設定しておく
	// 一枚目用
	private int myStartPosLeft = 0;
	private int myStartPosTop = 0;
	private int myStopPosLeft = 0;
	private int myStopPosTop = 0;
	private int rivalStartPosLeft = 0;
	private int rivalStartPosTop = 0;
	private int rivalStopPosLeft = 0;
	private int rivalStopPosTop = 0;

	private int counter = 0;
	private int myBaseLeft = 0;
	private int myBaseTop = 0;
	private int rivalBaseLeft = 0;
	private int rivalBaseTop = 0;
	
	// 二枚目用
	private int myStartPosLeft2 = 0;
	private int myStartPosTop2 = 0;
	private int myStopPosLeft2 = 0;
	private int myStopPosTop2 = 0;
	private int rivalStartPosLeft2 = 0;
	private int rivalStartPosTop2 = 0;
	private int rivalStopPosLeft2 = 0;
	private int rivalStopPosTop2 = 0;
	
	private int counter2 = 0;
	private int myBaseLeft2 = 0;
	private int myBaseTop2 = 0;
	private int rivalBaseLeft2 = 0;
	private int rivalBaseTop2 = 0;

	// 三枚目用
	private int myStartPosLeft3 = 0;
	private int myStartPosTop3 = 0;
	private int myStopPosLeft3 = 0;
	private int myStopPosTop3 = 0;
	private int rivalStartPosLeft3 = 0;
	private int rivalStartPosTop3 = 0;
	private int rivalStopPosLeft3 = 0;
	private int rivalStopPosTop3 = 0;
	
	private int counter3 = 0;
	private int myBaseLeft3 = 0;
	private int myBaseTop3 = 0;
	private int rivalBaseLeft3 = 0;
	private int rivalBaseTop3 = 0;

	// 四枚目用
	private int myStartPosLeft4 = 0;
	private int myStartPosTop4 = 0;
	private int myStopPosLeft4 = 0;
	private int myStopPosTop4 = 0;
	private int rivalStartPosLeft4 = 0;
	private int rivalStartPosTop4 = 0;
	private int rivalStopPosLeft4 = 0;
	private int rivalStopPosTop4 = 0;
	
	private int counter4 = 0;
	private int myBaseLeft4 = 0;
	private int myBaseTop4 = 0;
	private int rivalBaseLeft4 = 0;
	private int rivalBaseTop4 = 0;

	// 五枚目用
	private int myStartPosLeft5 = 0;
	private int myStartPosTop5 = 0;
	private int myStopPosLeft5 = 0;
	private int myStopPosTop5 = 0;
	private int rivalStartPosLeft5 = 0;
	private int rivalStartPosTop5 = 0;
	private int rivalStopPosLeft5 = 0;
	private int rivalStopPosTop5 = 0;
	
	private int counter5 = 0;
	private int myBaseLeft5 = 0;
	private int myBaseTop5 = 0;
	private int rivalBaseLeft5 = 0;
	private int rivalBaseTop5 = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        Log.d("Create","start");
        this.getThis();
        this.displayCards();
	}
	
    public void getThis(){
    	this.bAct = this;
    }
    public void finishOnClick(View v){
    	
//		Intent intent = new Intent(BattleActivity.this, MainMenuActivity.class);
//		startActivity(intent);
		// 移動する的なやつにする。
		this.startMovingCard(50+((this.cardCount+3)*5), 225, 0, 380, 50+((30-this.cardCount-3)*5), 125, 260, 0);
		this.startMovingCard2(50+((this.cardCount+2)*5), 225, 65, 380, 50+((30-this.cardCount-2)*5), 125, 195, 0);
		this.startMovingCard3(50+((this.cardCount+1)*5), 225, 130, 380, 50+((30-this.cardCount-1)*5), 125, 130, 0);
		this.startMovingCard4(50+(this.cardCount*5), 225, 195, 380, 50+((30-this.cardCount)*5), 125, 65, 0);
		this.startMovingCard5(50+((this.cardCount-1)*5), 225, 260, 380, 50+((30-this.cardCount+1)*5), 125, 0, 0);
		this.setFinishCard();
    }
    
/*	public void battleCardsSet(){
		BattleCardsSet bcs = new BattleCardsSet(this);
	
		
		setContentView(bcs);
	}
*/
	/**
	 * 手札カードを表示する
	 * @param left
	 * @param top
	 */
	public void displayCards() {
		
		this.cardEvent = false;
		
		for(int i = this.cardCount - 1; i >= 0; i--){
			// 相手CARD用View取得
			this.rivalViewCard[i] = ((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			// ユーザーCARD用View取得
			this.myViewCard[i] = ((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
//					R.layout.card_detailview, null);
					R.layout.my_cards, null);
			// OntouchListnerにActivityクラスを設定
			this.myViewCard[i].setOnTouchListener(this);
			// カードを長押しした場合のイベントリスナ
			final Activity act = bAct;
			this.myViewCard[i].setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					
					// ボタン長押しでカード詳細画面を表示
					viewDetailCards(40, 10);
					
					return false;
				}
			});

			// 相手Densityの値を取得
			float tmpDensity = this.getResources().getDisplayMetrics().density;
			
			BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((50+(i*5))*tmpDensity), (int)(125*tmpDensity), 0, 0);
			
			// 戦闘画面のベース部品を取得
			BattleCardDetail vgroup = (BattleCardDetail)this.findViewById(R.id.battle_base_layout);

			// 戦闘ベース部品にcard追加する
			vgroup.addView(this.rivalViewCard[i], cartParams);

			// ユーザーDensityの値を取得
//			tmpDensity = this.getResources().getDisplayMetrics().density;
			
			cartParams = new BattleCardDetail.LayoutParams(
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((50+((this.cardCount-i)*5))*tmpDensity), (int)(225*tmpDensity), 0, 0);
			
			// 戦闘画面のベース部品を取得
			vgroup = (BattleCardDetail)this.findViewById(R.id.battle_base_layout);

			// 戦闘ベース部品にcard追加する
			vgroup.addView(this.myViewCard[i], cartParams);
		}
		// 次回山札表示時３枚減らすため
		this.cardCount = this.cardCount - 3;
	}
	
	
	/**
	 * カードを指定座標に配置する
	 * @param left
	 * @param top
	 */
//	private void moveCard(int width, int height, int backwidth, int backheight) {
	private void moveCard(int width, int height, int viewNo) {
		

		Log.d("★", "getAction()" + "■ACTION_MOVE" + " width:" + width + "  height:" + height);

		int i = 0;
/*		if(backwidth < 100){
			i = 0;
			backwidth = 0;
		}
		if(100 <= backwidth && backwidth < 200){
			i = 1;
			backwidth = 65;
		}
		if(200 <= backwidth && backwidth < 300){
			i = 2;
			backwidth = 130;
		}
		if(300 <= backwidth && backwidth < 400){
			i = 3;
			backwidth = 195;
		}
		if(400 <= backwidth){
			i = 4;
			backwidth = 260;
		}
*/
		if(viewNo == 0){
			i = 0;
			width = 0;
		}
		if(viewNo == 1){
			i = 1;
			width = 65;
		}
		if(viewNo == 2){
			i = 2;
			width = 130;
		}
		if(viewNo == 3){
			i = 3;
			width = 195;
		}
		if(viewNo == 4){
			i = 4;
			width = 260;
		}

		// Densityの値を取得
		float tmpDensity = this.getResources().getDisplayMetrics().density;

		// カードを押さえてカードより上の座標ずらしたら、カードを上にずらす。
		if (height <= 0) {
			BattleCardDetail.LayoutParams params = (BattleCardDetail.LayoutParams)this.myViewCard[i].getLayoutParams();
			params.setMargins((int)(width*tmpDensity), (int)((380 - 20)* tmpDensity), 0, 0);
			this.myViewCard[i].setLayoutParams(params);

		}

		// カードを押さえてカードより下の座標にずらしたらカードを元の位置にに戻す
		if (height >= myViewCard[i].getHeight()) {
			BattleCardDetail.LayoutParams params = (BattleCardDetail.LayoutParams)this.myViewCard[i].getLayoutParams();
			params.setMargins((int)(width*tmpDensity), (int)((380)* tmpDensity), 0, 0);
			this.myViewCard[i].setLayoutParams(params);
		}
		this.myViewCard[i].invalidate();

	}
	
	/**
	 * カード詳細を画面に表示する
	 * @param left
	 * @param top
	 */
	public void viewDetailCards(int left, int top) {
		

		// Densityの値を取得
		float tmpDensity = this.getResources().getDisplayMetrics().density;

		// 戦闘画面のベース部品を取得
		this.vgroup = (BattleCardDetail)this.findViewById(R.id.battle_base_layout);

		// CARD用View取得
		this.myViewCardDerail = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.card_detailview, null);
		
		BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
				BattleCardDetail.LayoutParams.WRAP_CONTENT,
				BattleCardDetail.LayoutParams.WRAP_CONTENT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		

		// 戦闘ベース部品にカード詳細を追加する
		this.vgroup.addView(this.myViewCardDerail, cartParams);
		
	}
	
	/**
	 * カード詳細を消す
	 */
	private void invisibleCardDetail() {
		
		if (vgroup == null) {
			return;
		}
		// 親グループからカード詳細を外す
		this.vgroup.removeView(this.myViewCardDerail);
		// 画面再描画を要求
		this.vgroup.invalidate();
	}


	@Override
	public boolean onTouch(View view, MotionEvent event) {
		
		int action = event.getAction();
		
		int viewNo = 0;
		
		for(int i = 0; i <= 4; i++){
			if(view == myViewCard[i]){
				viewNo = i;
			}
		}

		switch(action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.d("TouchEvent", "getAction()" + "■ACTION_DOWN");
			
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			Log.d("TouchEvent", "getAction()" + "■ACTION_UP");
			// 詳細画面表示を消す
			this.invisibleCardDetail();
			
			break;
		case MotionEvent.ACTION_MOVE:
			// ボタンが押されている場所にカードを移動する。
			this.moveCard((int)(event.getX()), (int)(event.getY()), viewNo);
			
			break;
		}
		return false;
	}
	/**
	 * カード移動開始
	 */
	private void startMovingCard(int myStartX, int myStartY, int myStopX, int myStopY, int rivalStartX, int rivalStartY, int rivalStopX, int rivalStopY){
		
		this.myStartPosLeft = myStartX;
		this.myStartPosTop = myStartY;
		this.myStopPosLeft = myStopX;
		this.myStopPosTop = myStopY;
		this.rivalStartPosLeft = rivalStartX;
		this.rivalStartPosTop = rivalStartY;
		this.rivalStopPosLeft = rivalStopX;
		this.rivalStopPosTop = rivalStopY;

		// 新たなハンドラを追加する前に、ハンドラにある既存のコールバックをすべて削除
		this.mHandler.removeCallbacks(this.mUpdateTimeTask);

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.mUpdateTimeTask, 100);

	}
	private void startMovingCard2(int myStartX, int myStartY, int myStopX, int myStopY, int rivalStartX, int rivalStartY, int rivalStopX, int rivalStopY){
		
		this.myStartPosLeft2 = myStartX;
		this.myStartPosTop2 = myStartY;
		this.myStopPosLeft2 = myStopX;
		this.myStopPosTop2 = myStopY;
		this.rivalStartPosLeft2 = rivalStartX;
		this.rivalStartPosTop2 = rivalStartY;
		this.rivalStopPosLeft2 = rivalStopX;
		this.rivalStopPosTop2 = rivalStopY;
		

		// 新たなハンドラを追加する前に、ハンドラにある既存のコールバックをすべて削除
		this.mHandler.removeCallbacks(this.mUpdateTimeTask2);

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.mUpdateTimeTask2, 100);
	}
	private void startMovingCard3(int myStartX, int myStartY, int myStopX, int myStopY, int rivalStartX, int rivalStartY, int rivalStopX, int rivalStopY){
		
		this.myStartPosLeft3 = myStartX;
		this.myStartPosTop3 = myStartY;
		this.myStopPosLeft3 = myStopX;
		this.myStopPosTop3 = myStopY;
		this.rivalStartPosLeft3 = rivalStartX;
		this.rivalStartPosTop3 = rivalStartY;
		this.rivalStopPosLeft3 = rivalStopX;
		this.rivalStopPosTop3 = rivalStopY;
		

		// 新たなハンドラを追加する前に、ハンドラにある既存のコールバックをすべて削除
		this.mHandler.removeCallbacks(this.mUpdateTimeTask3);

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.mUpdateTimeTask3, 100);
	}
	private void startMovingCard4(int myStartX, int myStartY, int myStopX, int myStopY, int rivalStartX, int rivalStartY, int rivalStopX, int rivalStopY){
		
		this.myStartPosLeft4 = myStartX;
		this.myStartPosTop4 = myStartY;
		this.myStopPosLeft4 = myStopX;
		this.myStopPosTop4 = myStopY;
		this.rivalStartPosLeft4 = rivalStartX;
		this.rivalStartPosTop4 = rivalStartY;
		this.rivalStopPosLeft4 = rivalStopX;
		this.rivalStopPosTop4 = rivalStopY;
		

		// 新たなハンドラを追加する前に、ハンドラにある既存のコールバックをすべて削除
		this.mHandler.removeCallbacks(this.mUpdateTimeTask4);

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.mUpdateTimeTask4, 100);
	}
	private void startMovingCard5(int myStartX, int myStartY, int myStopX, int myStopY, int rivalStartX, int rivalStartY, int rivalStopX, int rivalStopY){
		
		this.myStartPosLeft5 = myStartX;
		this.myStartPosTop5 = myStartY;
		this.myStopPosLeft5 = myStopX;
		this.myStopPosTop5 = myStopY;
		this.rivalStartPosLeft5 = rivalStartX;
		this.rivalStartPosTop5 = rivalStartY;
		this.rivalStopPosLeft5 = rivalStopX;
		this.rivalStopPosTop5 = rivalStopY;
		

		// 新たなハンドラを追加する前に、ハンドラにある既存のコールバックをすべて削除
		this.mHandler.removeCallbacks(this.mUpdateTimeTask5);

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.mUpdateTimeTask5, 100);
	}
	
	/**
	 * 山札を消す
	 */
	private void setFinishCard() {
		// 新たなハンドラを追加する前に、ハンドラにある既存のコールバックをすべて削除
		this.mHandler.removeCallbacks(this.setFinishCard);

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.setFinishCard, 1000);
		
	}
	
	/**
	 * カード移動停止(途中も可能)
	 */
	private void stopMovingCard(){
		
		// キューに溜まって待ち状態のコールバックイベントをキャンセルする
		this.mHandler.removeCallbacks(this.mUpdateTimeTask);
		
		status = false;
		
	}
	private void stopMovingCard2(){
		
		// キューに溜まって待ち状態のコールバックイベントをキャンセルする
		this.mHandler.removeCallbacks(this.mUpdateTimeTask2);
		
		status2 = false;
		
	}
	private void stopMovingCard3(){
		
		// キューに溜まって待ち状態のコールバックイベントをキャンセルする
		this.mHandler.removeCallbacks(this.mUpdateTimeTask3);
		
		status2 = false;
		
	}
	private void stopMovingCard4(){
		
		// キューに溜まって待ち状態のコールバックイベントをキャンセルする
		this.mHandler.removeCallbacks(this.mUpdateTimeTask4);
		
		status2 = false;
		
	}
	private void stopMovingCard5(){
		
		// キューに溜まって待ち状態のコールバックイベントをキャンセルする
		this.mHandler.removeCallbacks(this.mUpdateTimeTask5);
		
		status2 = false;
		
	}
	
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			// 移動中の表示回数
			int time = maxTime;
			// 座標移動
			// 状態
			if (status == false) {
				// 最初だけ実施する
				// 座標の差分を算出
				int mySabunLeft = myStopPosLeft - myStartPosLeft;
				int mySabunTop = myStopPosTop - myStartPosTop;
				myBaseLeft = mySabunLeft/time;
				myBaseTop = mySabunTop/time;

				int rivalSabunLeft = rivalStopPosLeft - rivalStartPosLeft;
				int rivalSabunTop = rivalStopPosTop - rivalStartPosTop;
				rivalBaseLeft = rivalSabunLeft/time;
				rivalBaseTop = rivalSabunTop/time;

				
				// 状態を（移動）に設定
				status = true;
			}
			
			int myPosLeft = myStartPosLeft + counter * myBaseLeft;
			int myPosTop = myStartPosTop + counter * myBaseTop;
			
			int rivalPosLeft = rivalStartPosLeft + counter * rivalBaseLeft;
			int rivalPosTop = rivalStartPosTop + counter * rivalBaseTop;

			Log.d("★★★★", "status:" + status + " posLeft:" + myPosLeft + " posTop:" + myPosTop);
			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;
			BattleCardDetail.LayoutParams myParams = (BattleCardDetail.LayoutParams)myViewCard[0].getLayoutParams();
			BattleCardDetail.LayoutParams rivalParams = (BattleCardDetail.LayoutParams)rivalViewCard[0].getLayoutParams();
			
			if (counter <= time) {
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 100ms後に、コールバックメソッド（自メソッド）を実行するように設定
				mHandler.postDelayed(mUpdateTimeTask, 100);
				
				counter++;
			}
			else {
				myPosLeft = myStopPosLeft;
				myPosTop = myStopPosTop;
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalPosLeft = rivalStopPosLeft;
				rivalPosTop = rivalStopPosTop;
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 停止処理
				stopMovingCard();
				counter = 0;
			}
			
			// カードの位置確定と表示
			myViewCard[0].setLayoutParams(myParams);
			myViewCard[0].invalidate();
			rivalViewCard[0].setLayoutParams(rivalParams);
			rivalViewCard[0].invalidate();
				
		}
	};
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mUpdateTimeTask2 = new Runnable() {
		public void run() {
			// 移動中の表示回数
			int time = maxTime;
			// 座標移動
			// 状態
			if (status2 == false) {
				// 最初だけ実施する
				// 座標の差分を算出
				int mySabunLeft = myStopPosLeft2 - myStartPosLeft2;
				int mySabunTop = myStopPosTop2 - myStartPosTop2;
				myBaseLeft2 = mySabunLeft/time;
				myBaseTop2 = mySabunTop/time;

				int rivalSabunLeft = rivalStopPosLeft2 - rivalStartPosLeft2;
				int rivalSabunTop = rivalStopPosTop2 - rivalStartPosTop2;
				rivalBaseLeft2 = rivalSabunLeft/time;
				rivalBaseTop2 = rivalSabunTop/time;
				
				// 状態を（移動）に設定
				status2 = true;
			}
			
			int myPosLeft = myStartPosLeft2 + counter2 * myBaseLeft2;
			int myPosTop = myStartPosTop2 + counter2 * myBaseTop2;
			
			int rivalPosLeft = rivalStartPosLeft2 + counter2 * rivalBaseLeft2;
			int rivalPosTop = rivalStartPosTop2 + counter2 * rivalBaseTop2;

			Log.d("★★★★", "status2:" + status2 + " posLeft:" + myPosLeft + " posTop:" + myPosTop);
			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;
			BattleCardDetail.LayoutParams myParams = (BattleCardDetail.LayoutParams)myViewCard[1].getLayoutParams();
			BattleCardDetail.LayoutParams rivalParams = (BattleCardDetail.LayoutParams)rivalViewCard[1].getLayoutParams();
			
			if (counter2 <= time) {
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 100ms後に、コールバックメソッド（自メソッド）を実行するように設定
				mHandler.postDelayed(mUpdateTimeTask2, 100);
				
				counter2++;
			}
			else {
				myPosLeft = myStopPosLeft2;
				myPosTop = myStopPosTop2;
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalPosLeft = rivalStopPosLeft2;
				rivalPosTop = rivalStopPosTop2;
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 停止処理
				stopMovingCard2();
				counter2 = 0;
			}
			
			// カードの位置確定と表示
			myViewCard[1].setLayoutParams(myParams);
			myViewCard[1].invalidate();
			rivalViewCard[1].setLayoutParams(rivalParams);
			rivalViewCard[1].invalidate();
				
		}
	};
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mUpdateTimeTask3 = new Runnable() {
		public void run() {
			// 移動中の表示回数
			int time = maxTime;
			// 座標移動
			// 状態
			if (status3 == false) {
				// 最初だけ実施する
				// 座標の差分を算出
				int mySabunLeft = myStopPosLeft3 - myStartPosLeft3;
				int mySabunTop = myStopPosTop3 - myStartPosTop3;
				myBaseLeft3 = mySabunLeft/time;
				myBaseTop3 = mySabunTop/time;

				int rivalSabunLeft = rivalStopPosLeft3 - rivalStartPosLeft3;
				int rivalSabunTop = rivalStopPosTop3 - rivalStartPosTop3;
				rivalBaseLeft3 = rivalSabunLeft/time;
				rivalBaseTop3 = rivalSabunTop/time;
				
				// 状態を（移動）に設定
				status3 = true;
			}
			
			int myPosLeft = myStartPosLeft3 + counter3 * myBaseLeft3;
			int myPosTop = myStartPosTop3 + counter3 * myBaseTop3;
			
			int rivalPosLeft = rivalStartPosLeft3 + counter3 * rivalBaseLeft3;
			int rivalPosTop = rivalStartPosTop3 + counter3 * rivalBaseTop3;

			Log.d("★★★★", "status3:" + status3 + " posLeft:" + myPosLeft + " posTop:" + myPosTop);
			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;
			BattleCardDetail.LayoutParams myParams = (BattleCardDetail.LayoutParams)myViewCard[2].getLayoutParams();
			BattleCardDetail.LayoutParams rivalParams = (BattleCardDetail.LayoutParams)rivalViewCard[2].getLayoutParams();
			
			if (counter3 <= time) {
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 100ms後に、コールバックメソッド（自メソッド）を実行するように設定
				mHandler.postDelayed(mUpdateTimeTask3, 100);
				
				counter3++;
			}
			else {
				myPosLeft = myStopPosLeft3;
				myPosTop = myStopPosTop3;
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalPosLeft = rivalStopPosLeft3;
				rivalPosTop = rivalStopPosTop3;
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 停止処理
				stopMovingCard3();
				counter3 = 0;
			}
			
			// カードの位置確定と表示
			myViewCard[2].setLayoutParams(myParams);
			myViewCard[2].invalidate();
			rivalViewCard[2].setLayoutParams(rivalParams);
			rivalViewCard[2].invalidate();
				
		}
	};
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mUpdateTimeTask4 = new Runnable() {
		public void run() {
			// 移動中の表示回数
			int time = maxTime;
			// 座標移動
			// 状態
			if (status4 == false) {
				// 最初だけ実施する
				// 座標の差分を算出
				int mySabunLeft = myStopPosLeft4 - myStartPosLeft4;
				int mySabunTop = myStopPosTop4 - myStartPosTop4;
				myBaseLeft4 = mySabunLeft/time;
				myBaseTop4 = mySabunTop/time;

				int rivalSabunLeft = rivalStopPosLeft4 - rivalStartPosLeft4;
				int rivalSabunTop = rivalStopPosTop4 - rivalStartPosTop4;
				rivalBaseLeft4 = rivalSabunLeft/time;
				rivalBaseTop4 = rivalSabunTop/time;
				
				// 状態を（移動）に設定
				status4 = true;
			}
			
			int myPosLeft = myStartPosLeft4 + counter4 * myBaseLeft4;
			int myPosTop = myStartPosTop4 + counter4 * myBaseTop4;
			
			int rivalPosLeft = rivalStartPosLeft4 + counter4 * rivalBaseLeft4;
			int rivalPosTop = rivalStartPosTop4 + counter4 * rivalBaseTop4;

			Log.d("★★★★", "status4:" + status4 + " posLeft:" + myPosLeft + " posTop:" + myPosTop);
			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;
			BattleCardDetail.LayoutParams myParams = (BattleCardDetail.LayoutParams)myViewCard[3].getLayoutParams();
			BattleCardDetail.LayoutParams rivalParams = (BattleCardDetail.LayoutParams)rivalViewCard[3].getLayoutParams();
			
			if (counter4 <= time) {
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 100ms後に、コールバックメソッド（自メソッド）を実行するように設定
				mHandler.postDelayed(mUpdateTimeTask4, 100);
				
				counter4++;
			}
			else {
				myPosLeft = myStopPosLeft4;
				myPosTop = myStopPosTop4;
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalPosLeft = rivalStopPosLeft4;
				rivalPosTop = rivalStopPosTop4;
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 停止処理
				stopMovingCard4();
				counter4 = 0;
			}
			
			// カードの位置確定と表示
			myViewCard[3].setLayoutParams(myParams);
			myViewCard[3].invalidate();
			rivalViewCard[3].setLayoutParams(rivalParams);
			rivalViewCard[3].invalidate();
				
		}
	};
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mUpdateTimeTask5 = new Runnable() {
		public void run() {
			// 移動中の表示回数
			int time = maxTime;
			// 座標移動
			// 状態
			if (status5 == false) {
				// 最初だけ実施する
				// 座標の差分を算出
				int mySabunLeft = myStopPosLeft5 - myStartPosLeft5;
				int mySabunTop = myStopPosTop5 - myStartPosTop5;
				myBaseLeft5 = mySabunLeft/time;
				myBaseTop5 = mySabunTop/time;

				int rivalSabunLeft = rivalStopPosLeft5 - rivalStartPosLeft5;
				int rivalSabunTop = rivalStopPosTop5 - rivalStartPosTop5;
				rivalBaseLeft5 = rivalSabunLeft/time;
				rivalBaseTop5 = rivalSabunTop/time;
				
				// 状態を（移動）に設定
				status5 = true;
			}
			
			int myPosLeft = myStartPosLeft5 + counter5 * myBaseLeft5;
			int myPosTop = myStartPosTop5 + counter5 * myBaseTop5;
			
			int rivalPosLeft = rivalStartPosLeft5 + counter5 * rivalBaseLeft5;
			int rivalPosTop = rivalStartPosTop5 + counter5 * rivalBaseTop5;

			Log.d("★★★★", "status5:" + status5 + " posLeft:" + myPosLeft + " posTop:" + myPosTop);
			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;
			BattleCardDetail.LayoutParams myParams = (BattleCardDetail.LayoutParams)myViewCard[4].getLayoutParams();
			BattleCardDetail.LayoutParams rivalParams = (BattleCardDetail.LayoutParams)rivalViewCard[4].getLayoutParams();
			
			if (counter5 <= time) {
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 100ms後に、コールバックメソッド（自メソッド）を実行するように設定
				mHandler.postDelayed(mUpdateTimeTask5, 100);
				
				counter5++;
			}
			else {
				myPosLeft = myStopPosLeft5;
				myPosTop = myStopPosTop5;
				myParams.setMargins((int)(myPosLeft*tmpDensity), (int)(myPosTop*tmpDensity), 0, 0);
				rivalPosLeft = rivalStopPosLeft5;
				rivalPosTop = rivalStopPosTop5;
				rivalParams.setMargins((int)(rivalPosLeft*tmpDensity), (int)(rivalPosTop*tmpDensity), 0, 0);
				
				// 停止処理
				stopMovingCard5();
				counter5 = 0;
			}
			
			// カードの位置確定と表示
			myViewCard[4].setLayoutParams(myParams);
			myViewCard[4].invalidate();
			rivalViewCard[4].setLayoutParams(rivalParams);
			rivalViewCard[4].invalidate();
				
		}
	};
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable setFinishCard = new Runnable() {
		public void run() {
			// 戦闘画面のベース部品を取得
			BattleCardDetail vgroup = (BattleCardDetail)findViewById(R.id.battle_base_layout);
			
			// 山札として残っているカードを削除
			for(int i = cardCount + 2; i >= 5; i--){
				vgroup.removeView(myViewCard[i]);
				vgroup.removeView(rivalViewCard[i]);
			}
			// ユーザー手札を削除し、表面を表示させる
			for(Integer i = 0; i <= 4; i++){
				vgroup.removeView(myViewCard[i]);
				// ユーザーCARD用View再取得
				myViewCard[i] = ((LayoutInflater) 
						getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
//						R.layout.my_cards, null);
						R.layout.card_detailview, null);
				// OntouchListnerにActivityクラスを設定
				myViewCard[i].setOnTouchListener(bAct);
				// カードを長押しした場合のイベントリスナ
//				final Activity act = bAct;
				myViewCard[i].setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						
						// ボタン長押しでカード詳細画面を表示
						viewDetailCards(40, 10);
						
						return false;
					}
				});
				// 相手Densityの値を取得
				float tmpDensity = getResources().getDisplayMetrics().density;
				
				BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
						(int)(getResources().getDimensionPixelSize(R.dimen.card_width)),
						(int)(getResources().getDimensionPixelSize(R.dimen.card_height)));
/*				View view = getLayoutInflater().inflate(R.layout.card_detailview, null);
				// 名前設定
				((TextView)view.findViewById(R.id.carddetail_name)).setText("name");
				((TextView)view.findViewById(R.id.carddetail_name)).setTextSize(5.0f);
				// 説明設定
				((TextView)view.findViewById(R.id.carddetail_atk)).setText("攻：" + "1000");
				((TextView)view.findViewById(R.id.carddetail_atk)).setTextSize(5.0f);
				// 説明設定
				((TextView)view.findViewById(R.id.carddetail_def)).setText("守：" + "300");
				((TextView)view.findViewById(R.id.carddetail_def)).setTextSize(5.0f);
				// 説明設定
				((TextView)view.findViewById(R.id.carddetail_intoro)).setText("説明：" + "せつめいと読みます");
				((TextView)view.findViewById(R.id.carddetail_intoro)).setTextSize(5.0f);
				// 画像設定
				((ImageView)view.findViewById(R.id.carddetail_icon)).setImageResource(R.drawable.beetle1);
				((ImageView)view.findViewById(R.id.carddetail_icon)).setScaleX(10.0f);
				// 画像設定
				((ImageView)view.findViewById(R.id.carddetail_attrribute)).setImageResource(R.drawable.wind);
				((ImageView)view.findViewById(R.id.carddetail_attrribute)).setScaleX(20.0f);
				vgroup.addView(view);
*/				
				cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				cartParams.setMargins((int)((i*65)*tmpDensity), (int)(380*tmpDensity), 0, 0);
				
				// 戦闘画面のベース部品を取得
				vgroup = (BattleCardDetail)findViewById(R.id.battle_base_layout);

				// 戦闘ベース部品にcard追加する
				vgroup.addView(myViewCard[i], cartParams);
			}
			
			
			// 画面再描画を要求
			vgroup.invalidate();
				

		}
	};
	
}
