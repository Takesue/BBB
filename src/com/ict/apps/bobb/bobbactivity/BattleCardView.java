package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.battle.BattleSceneCardSelection;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.CardAttribute;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 対戦時に表示されるカードのViewクラス
 */
public class BattleCardView extends LinearLayout {
	
	private BattleActivity activity = null;

	// カードUPフラグ
	private boolean upFlag = false;

	public BattleCardView(Context context) {
		super(context);
	}

	public BattleCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		int action = event.getAction();

		switch(action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.d("TouchEvent", "getAction()" + "■ACTION_DOWN");
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			Log.d("TouchEvent", "getAction()" + "■ACTION_UP");

			// ボタンから手が離れる
			this.activity.actionUpCard(this);
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			// ボタンが押されている場所にカードを移動する。
			this.moveCard((int)(event.getX()), (int)(event.getY()), this);
			
			break;
		}
		
		return true;
	}

	/**
	 * アクティビティを設定
	 * @param activity
	 */
	public void setControlActivity(BattleActivity activity) {
		this.activity = activity;
	}

	/**
	 * カードを指定座標に配置する
	 * @param left
	 * @param top
	 */
	private void moveCard(int width, int height, View view) {
		
		Log.d("★", "getAction()" + "■ACTION_MOVE" + " width:" + width + "  height:" + height);

		// カードを押さえてカードより上の座標ずらしたら、カードを上にずらす。
		if (height <= 0) {
			
			if ((this.upFlag == false) && (!BattleSceneCardSelection.isThreeCardselected())){
				// カードが上にあがったときは第二引数：0
				this.activity.moveCard(this, 0);
				this.upFlag = true;
			}
		}
		else if (height >= view.getHeight()) {
			// カードを押さえてカードより下の座標にずらしたらカードを元の位置にに戻す
			// カードが下に下がったときは第二引数：1
			if (this.upFlag == true) {
				this.activity.moveCard(this, 1);
				this.upFlag = false;
			}
		}
	}
	
	/**
	 * Ｕｐフラグのセッター
	 * @param flag
	 */
	public void setUpFlag(boolean flag) {
		this.upFlag = flag;
	}

	// --------------------------------------------------------
	// カード移動する動きを実装する的なやつ
	// --------------------------------------------------------

	public boolean moveFlag = false;
	
	// ハンドラーを取得
	private Handler mHandler = new Handler();
	
	// 移動の開始位置と終了位置を設定しておく
	private int startPosLeft = 0;
	private int startPosTop = 0;
	private int stopPosLeft = 0;
	private int stopPosTop = 0;
	
	
	private View moveView = null;
	
	private int counter = 0;
	private float baseLeft = 0;
	private float baseTop = 0;
	
	/**
	 * 座標X取得
	 * @return
	 */
	public int getStartPosLeft() {
		return startPosLeft;
	}

	/**
	 * 座標Y取得
	 * @return
	 */
	public int getStartPosTop() {
		return startPosTop;
	}

	
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mMoveCardTask = new Runnable() {
		public void run() {
			// 移動中の表示回数
			int time = 3;
			
			// 座標移動
			// 状態
			if (moveFlag == false) {
				// 最初だけ実施する
				// 座標の差分を算出
				float sabunLeft = stopPosLeft - startPosLeft;
				float sabunTop = stopPosTop - startPosTop;
				baseLeft = sabunLeft/time;
				baseTop = sabunTop/time;
				
				// 状態を（移動）に設定
				moveFlag = true;
			}
			
			float posLeft = startPosLeft + counter * baseLeft;
			float posTop = startPosTop + counter * baseTop;

			Log.d("★★★★", "status:" + moveFlag + " posLeft:" + posLeft + " posTop:" + posTop);

			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;

			BattleLayout.LayoutParams params = (BattleLayout.LayoutParams)moveView.getLayoutParams();
			
			if (counter <= time) {
				params.setMargins((int)(posLeft*tmpDensity), (int)(posTop*tmpDensity), 0, 0);
				
				// 100ms後に、コールバックメソッド（自メソッド）を実行するように設定
				mHandler.postDelayed(mMoveCardTask, 100);
				
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
			moveView.setLayoutParams(params);
			moveView.invalidate();
		}
	};

	/**
	 * カード移動開始
	 */
	public void startMovingCard(int stopX, int stopY){

		this.stopPosLeft = stopX;
		this.stopPosTop = stopY;
		this.moveView = this;

		// Handler に対し、" 100 ms 後に mUpdateTimeTask() を呼び出す
		this.mHandler.postDelayed(this.mMoveCardTask, 10);
		
	}
	
	/**
	 * カード移動停止(途中も可能)
	 */
	private void stopMovingCard(){
		
		// キューに溜まって待ち状態のコールバックイベントをキャンセルする
		this.mHandler.removeCallbacks(this.mMoveCardTask);
		
		this.moveFlag = false;
		
		// 停止位置を開始位置に設定する
		this.setPosXY(this.stopPosLeft, this.stopPosTop);
	}

	
	// カード情報を保持する
	private BeetleCard cardInfo;
	
	public BeetleCard getCardInfo(){
		return this.cardInfo;
	}
	
	/**
	 * カード情報を設定する
	 * @param cardInfo
	 */
	public void setBeetleCard(BeetleCard cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	
	/**
	 * カードを裏にする
	 * カード裏の画像のみの表示に設定する
	 */
	public void flippedCardBack() {
		// 背景設定
		this.setBackgroundResource(R.drawable.cards);
		
		// 画像なし
		((ImageView)this.findViewById(R.id.myCardImage)).setImageBitmap(null);

		// 属性なし
		((ImageView)this.findViewById(R.id.myCardAttr)).setImageBitmap(null);

		// 攻、守
		((TextView)this.findViewById(R.id.myCardAD)).setText("");

		// 表示文字なし
		((TextView)this.findViewById(R.id.myCardValue)).setText("");
		
	}
	
	
	
	
	/**
	 * カードを表にする
	 * カード情報をカードに設定する
	 */
	public void flippedCardFace() {
		
		// 画像設定
		((ImageView)this.findViewById(R.id.myCardImage)).setImageResource(this.cardInfo.getImageResourceId(this.activity));
		
		// 属性設定
		CardAttribute attr = this.cardInfo.getAttribute();
		if (attr == CardAttribute.FIRE) {
			((ImageView)this.findViewById(R.id.myCardAttr)).setImageResource(R.drawable.fire);
		}
		else if (attr == CardAttribute.WATER) {
			((ImageView)this.findViewById(R.id.myCardAttr)).setImageResource(R.drawable.water);
		}
		else if (attr == CardAttribute.WIND) {
			((ImageView)this.findViewById(R.id.myCardAttr)).setImageResource(R.drawable.wind);
		}

		int type = this.cardInfo.getType();
		
		if (type == 3) {
			// 攻撃カード
			
			// 背景設定
			this.setBackgroundResource(R.drawable.card_red);
			
			// 攻、守
			((TextView)this.findViewById(R.id.myCardAD)).setText("攻撃：");
			// 表示文字設定
			((TextView)this.findViewById(R.id.myCardValue)).setText(
					Integer.toString(this.cardInfo.getAttack()));
		}
		if (type == 4) {
			// 守備カード

			// 背景設定
			this.setBackgroundResource(R.drawable.card_blue);
			
			// 攻、守
			((TextView)this.findViewById(R.id.myCardAD)).setText("守備：");
			// 表示文字設定
			((TextView)this.findViewById(R.id.myCardValue)).setText(
					Integer.toString(this.cardInfo.getDefense()));
		}
	}

	/**
	 * 現時点の座標位置を設定
	 * @param x
	 * @param y
	 */
	public void setPosXY(int x, int y) {
		this.startPosLeft = x;
		this.startPosTop = y;
	}
	
	
	
	/**
	 *  別スレッドからカードの表を表示させる。
	 */
	public void callFlippedCardFace() {
		this.mHandler.post(new Runnable() {
			public void run() {
				// カード表表示メソッド呼び出し
				flippedCardFace();
			}
		});
	}


}
