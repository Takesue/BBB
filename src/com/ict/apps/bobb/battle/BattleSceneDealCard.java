package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleLayout;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 対戦時のカードを配布するシーンのクラス
 *
 */
public class BattleSceneDealCard implements BattleScene {
	
	private BattleActivity activity = null;
	
	// 山札の表示開始位置を定義
	private final int[][] initialCardsPos = 
		{
			{50,225,5,380},	// 自分
			{50,125,5, 20}	// 相手
		};
	
	// 手札の左右のマージン
	private final int leftMargin = 5;
	// 手札のY座標
	private final int myCardsPosY = 380;
	
	private final int enemyCardsPosY = 20;
	
	
	/**
	 * コンストラクタ
	 * @param activity 対戦画面のアクティビティ
	 */
	public BattleSceneDealCard(BattleActivity activity) {
		this.activity = activity;
	}

	@Override
	public void init() {

		// 相手情報（Name）
		((TextView)this.activity.findViewById(R.id.battle_enemyName)).setText("対戦相手 : " + this.activity.enemyPlayer.getName());

		// 相手情報（LP）
		((TextView)this.activity.findViewById(R.id.battle_enemyLp)).setText("LP : " + this.activity.enemyPlayer.getLifepoint());

		// ユーザ情報（LP）
		((TextView)this.activity.findViewById(R.id.battle_myLp)).setText("LP : " + this.activity.myPlayer.getLifepoint());

		// ユーザ情報（制限時間）
		((TextView)this.activity.findViewById(R.id.battle_timelimit)).setText("制限時間 :0秒");

		// 配布ボタンを表示する。
		this.setDealButton();
		
		// ２ターン以降であれば、手札を端に表示する
		if(this.activity.dealflg){
			this.dealConpCard(0);
			this.dealConpCard(1);
		}
		else {
			// カードをシャッフルする。
			this.activity.myPlayer.cardInfo.shuffle();
			this.activity.enemyPlayer.cardInfo.shuffle();
			
			// 配布予定カードのindexを初期化する
//			this.activity.myInfo.curPos = 0;
//			this.activity.enemyInfo.curPos = 0;

		}
		
		
		// 山札を表示する
		// プレイヤ分
		// 相手分
		this.displayCards(0);
		
		this.displayCards(1);
		
	}

	@Override
	public void moveCard(BattleCardView view, int action) {
	}

	@Override
	public void onLongClickCard(BattleCardView view) {
	}

	@Override
	public void finish() {
		// 表示しているビューを全て削除する
		this.activity.baseLayout.removeAllViews();
	}
	
	@Override
	public void actionUpCard(BattleCardView view) {
	}
	
	/**
	 * カードを表示する
	 * @param type
	 */
	public void displayCards(int type) {
		
		ArrayList<BattleCardView> viewCards = null;
		// 自分のカードを全部取得
		if (type == 0) {
			viewCards = this.activity.myPlayer.cardInfo.getUnUsedCard();
			
		}
		else {
			viewCards = this.activity.enemyPlayer.cardInfo.getUnUsedCard();
		}
		
		int length = viewCards.size();
		for (int i = 0; i < length; i++) {
			// Densityの値を取得
			float tmpDensity = this.activity.getResources().getDisplayMetrics().density;

			BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.card_width)),
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((this.initialCardsPos[type][0] + (5*i)) *tmpDensity), (int)(this.initialCardsPos[type][1]*tmpDensity), 0, 0);
			viewCards.get(i).setPosXY((int)((this.initialCardsPos[type][0] + (5*i)) ), (int)(this.initialCardsPos[type][1]));
			
			viewCards.get(i).flippedCardBack();
			
			// 戦闘ベース部品にcard追加する
			this.activity.baseLayout.addView(viewCards.get(i), 0, cartParams);
		}
		
	}
	
	/**
	 * 残った手札カードを表示する
	 * @param type
	 */
	public void dealConpCard(int type) {
		
		ArrayList<BattleCardView> viewCards = null;
		
		
		// 自分のカードを全部取得
		if (type == 0) {
			viewCards = this.activity.myPlayer.cardInfo.getHoldCard();
			
		}
		else {
			viewCards = this.activity.enemyPlayer.cardInfo.getHoldCard();
		}
		
		int length = viewCards.size();
		for (int i = 0; i < length; i++) {
			// Densityの値を取得
			float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
			final int myCardMarginX = (int) ((new Float(this.activity.baseLayout.getWidth())/tmpDensity - (new Float(this.leftMargin)*2))/5);

			BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.card_width)),
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((this.initialCardsPos[type][2] + myCardMarginX*i) *tmpDensity), (int)(this.initialCardsPos[type][3]*tmpDensity), 0, 0);
			
			if(type == 0){
				viewCards.get(i).setPosXY((this.leftMargin + myCardMarginX*(i)) , this.myCardsPosY);
				viewCards.get(i).flippedCardFace();
			}else{
				viewCards.get(i).setPosXY((this.leftMargin + myCardMarginX*(i)) , this.enemyCardsPosY);
				viewCards.get(i).flippedCardBack();
			}
			
			// 戦闘ベース部品にcard追加する
			this.activity.baseLayout.addView(viewCards.get(i), 0, cartParams);
		}
		
	}
	
	/**
	 * 配るボタン画面に設定する
	 * @param type
	 */
	public void setDealButton() {

		Button button = new Button(this.activity);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				dealCardsOnClick(v);
				activity.finishOnClick(v);
			}
		});
		
		BattleLayout.LayoutParams params = new BattleLayout.LayoutParams(
				BattleLayout.LayoutParams.MATCH_PARENT,
				BattleLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.setMargins((int)(0), (int)(0), 0, 0);
		
		button.setLayoutParams(params);
		button.setBackgroundResource(R.drawable.battle_dealbutton);

		// 戦闘ベース部品にcard追加する
		this.activity.baseLayout.addView(button, params);

	}

	public void dealEnemyCards(int num) {
		
		// Densityの値を取得
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		final int count = num;
		final int myCardMarginX = (int) ((new Float(this.activity.baseLayout.getWidth())/tmpDensity - (new Float(this.leftMargin)*2))/5);
		
		// スレッド起動
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					BattleCardView[] cardList = new BattleCardView[count];
					int init = 5 - count;
					for (int i = init; i < 5; i++) {
						cardList[i - init] = activity.enemyPlayer.cardInfo.getNextCard();
						if(init <= count){
							// ３枚～５枚配り
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i) , enemyCardsPosY, 3);
						}else{
							// １枚～２枚配り
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i - (init - 2)) , enemyCardsPosY, 3);
						}
						
						Thread.sleep(200);
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
	
	
	
	/**
	 * カードを枚数分配る
	 * @param num
	 */
	public void dealCards(int num) {
		
		// Densityの値を取得
		final float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		final int count = num;
		final int myCardMarginX = (int) ((new Float(this.activity.baseLayout.getWidth())/tmpDensity - (new Float(this.leftMargin)*2))/5);
		
		// スレッド起動
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					
					BattleCardView[] cardList = new BattleCardView[count];
					int init = 5 - count;
					for (int i = init; i < 5; i++) {
						cardList[i - init] = activity.myPlayer.cardInfo.getNextCard();
						if(init <= count){
							// ３枚～５枚配り
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i) , myCardsPosY, 3);
						}else{
							// １枚～２枚配り
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i - (init - 2)) , myCardsPosY, 3);
						}
						
						Thread.sleep(200);
					}
					
					boolean flag = true;
					while(flag) {
						
						// 配り終わり待ちループ
						boolean cflag = false;
						for (int i = init; i < 5; i++) {
							if (cardList[i - init].moveFlag == true) {
								cflag = true;
							}
						}
						
						if (!cflag) {
							flag = false;
							for (int i = init; i < 5; i++) {
								cardList[i - init].callFlippedCardFace();
							}
							
							// シーンを変更
							callChangeNexrScene();
						}
						else {
							Thread.sleep(200);
						}
					}
				}
				catch (InterruptedException e) {
				}
			}
		}).start();

	}

	
//	/**
//	 * 配るボタン押下時に呼ばれる
//	 * @param v
//	 */
//	public void dealCardsOnClick(View v){
//		
//		// ボタンを表示から消す
//		this.activity.baseLayout.removeView(v);
//		
//		
//		
//		// スレッド起動
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					// 相手のカードを配る
//					dealEnemyDards(5);
//
//					// 自分のカードを配る
//					dealDards(5);
//				}
//				catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//
//	}
	

	// ハンドラー取得
	private Handler mHandler = new Handler();

	/**
	 * シーン変更（別スレッド呼び出し用）
	 */
	private void callChangeNexrScene() {
		
		// 別スレッドから呼ぶので、ハンドラーで実装する
		this.mHandler.post(new Runnable() {
			public void run() {
				activity.dealflg = true;
//				activity.changeNextScene();
				activity.bm.dealCardFinished();
			}
		});
	}

}
