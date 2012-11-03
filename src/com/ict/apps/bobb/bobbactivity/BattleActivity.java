package com.ict.apps.bobb.bobbactivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

public class BattleActivity extends Activity implements OnTouchListener {

	int i = 0;
	
	private BattleCardDetail vgroup;

	// カードの表示部品（card.xml）
	private View myViewCard[]    = new View[6];
	private View rivalViewCard[] = new View[6];

	// カード詳細表示部品(carddetail.xml)
	private View myViewCardDerail = null;
	
	// カードTOUCH時山札か、配布済みか判断
	boolean cardEvent = false;
	
	// カードが移動するアニメーションを作ってみる　利用したのは、ハンドラーの遅延でスレッド仕込むやつ。
	
	private boolean status = false;
	
	// ハンドラーを取得
	private Handler mHandler = new Handler();
	
	// 移動の開始位置と終了位置を設定しておく
	private int startPosLeft = 0;
	private int startPosTop = 0;
	private int stopPosLeft = 0;
	private int stopPosTop = 0;

	private int counter = 0;
	private int baseLeft = 0;
	private int baseTop = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        this.displayCards();
	}
	
    public void finishOnClick(View v){
    	
		Intent intent = new Intent(BattleActivity.this, MainMenuActivity.class);
		startActivity(intent);
		
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
		
		cardEvent = false;
		
		Log.d("1","1");
		for(int i = 0; i <= 4; i++){
			// 相手CARD用View取得
			this.rivalViewCard[i] = ((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			// ユーザーCARD用View取得
			this.myViewCard[i] = ((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			// OntouchListnerにActivityクラスを設定
			this.myViewCard[i].setOnTouchListener(this);
			// カードを長押しした場合のイベントリスナ
			final Activity act = this;
			Log.d("1","5");
			this.myViewCard[i].setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					
					// ボタン長押しでカード詳細画面を表示
					viewDetailCards(40, 10);
					
					return false;
				}
			});

			Log.d("1","6");
			// 相手Densityの値を取得
			float tmpDensity = this.getResources().getDisplayMetrics().density;
			
			BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((125+(i*5))*tmpDensity), (int)(125*tmpDensity), 0, 0);
			
			Log.d("1","7");
			// 戦闘画面のベース部品を取得
			BattleCardDetail vgroup = (BattleCardDetail)this.findViewById(R.id.battle_base_layout);

			Log.d("1","8");
			// 戦闘ベース部品にcard追加する
			vgroup.addView(this.rivalViewCard[i], cartParams);

			Log.d("1","9");
			// ユーザーDensityの値を取得
			tmpDensity = this.getResources().getDisplayMetrics().density;
			
			cartParams = new BattleCardDetail.LayoutParams(
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
					(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((125+(i*5))*tmpDensity), (int)(225*tmpDensity), 0, 0);
			
			Log.d("1","10");
			// 戦闘画面のベース部品を取得
			vgroup = (BattleCardDetail)this.findViewById(R.id.battle_base_layout);

			Log.d("1","11");
			// 戦闘ベース部品にcard追加する
			vgroup.addView(this.myViewCard[i], cartParams);
			
		}
		
	}
	
	
	/**
	 * カードを指定座標に配置する
	 * @param left
	 * @param top
	 */
	private void moveCard(int width, int height) {
		

		Log.d("★", "getAction()" + "■ACTION_MOVE" + " width:" + width + "  height:" + height);

		// Densityの値を取得
		float tmpDensity = this.getResources().getDisplayMetrics().density;

		// カードを押さえてカードより上の座標ずらしたら、カードを上にずらす。
		if (height <= 0) {
			BattleCardDetail.LayoutParams params = (BattleCardDetail.LayoutParams)this.myViewCard[0].getLayoutParams();
			params.setMargins((int)(10*tmpDensity), (int)((380 - 20)* tmpDensity), 0, 0);
			this.myViewCard[0].setLayoutParams(params);

		}

		// カードを押さえてカードより下の座標にずらしたらカードを元の位置にに戻す
		if (height >= myViewCard[0].getHeight()) {
			BattleCardDetail.LayoutParams params = (BattleCardDetail.LayoutParams)this.myViewCard[0].getLayoutParams();
			params.setMargins((int)(10*tmpDensity), (int)((380)* tmpDensity), 0, 0);
			this.myViewCard[0].setLayoutParams(params);
		}
		this.myViewCard[0].invalidate();

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

		if(status == false){
			// カード配布。
			this.startMovingCard(125, 225, 10, 380);
			this.status = true;
		}else{
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
				this.moveCard((int)(event.getX()), (int)(event.getY()));
				
				break;
			}
		}
		return false;
	}
	/**
	 * カード移動開始
	 */
	private void startMovingCard(int startX, int startY, int stopX, int stopY){
		
		this.startPosLeft = startX;
		this.startPosTop = startY;
		this.stopPosLeft = stopX;
		this.stopPosTop = stopY;

		// 新たなハンドラを追加する前に、ハンドラにある既存のコールバックをすべて削除
		this.mHandler.removeCallbacks(this.mUpdateTimeTask);

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.mUpdateTimeTask, 100);
	}
	
	/**
	 * カード移動停止(途中も可能)
	 */
	private void stopMovingCard(){
		
		// キューに溜まって待ち状態のコールバックイベントをキャンセルする
		this.mHandler.removeCallbacks(this.mUpdateTimeTask);
		
		status = false;
		
	}
	
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			for(Integer i = 0; i <= 4; i++){
			Log.d("2","1");
			Log.d("2", i.toString());
			// 移動中の表示回数
			int time = 10;
			// 座標移動
			// 状態
			if (status == false) {
				// 最初だけ実施する
				// 座標の差分を算出
				int sabunLeft = stopPosLeft - startPosLeft;
				int sabunTop = stopPosTop - startPosTop;
				baseLeft = sabunLeft/time;
				baseTop = sabunTop/time;
				
				// 状態を（移動）に設定
				status = true;
			}
			
			int posLeft = startPosLeft + counter * baseLeft;
			int posTop = startPosTop + counter * baseTop;

			Log.d("★★★★", "status:" + status + " posLeft:" + posLeft + " posTop:" + posTop);
			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;
			BattleCardDetail.LayoutParams params = (BattleCardDetail.LayoutParams)myViewCard[0].getLayoutParams();
			
			if (counter <= time) {
				params.setMargins((int)(posLeft*tmpDensity), (int)(posTop*tmpDensity), 0, 0);
				
				// 100ms後に、コールバックメソッド（自メソッド）を実行するように設定
				mHandler.postDelayed(mUpdateTimeTask, 100);
				
				counter++;
			}
			else {
				posLeft = stopPosLeft;
				posTop = stopPosTop;
				params.setMargins((int)(posLeft*tmpDensity), (int)(posTop*tmpDensity), 0, 0);
				
				// 停止処理
				stopMovingCard();
				counter = 0;
			}
			
			// カードの位置確定と表示
			Log.d("5","2");
			stopPosLeft = stopPosLeft + (i * 20);
			myViewCard[i].setLayoutParams(params);
			myViewCard[i].invalidate();
				
			}	
		}
	};

}
