package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleLayout;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.common.StatusInfo;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ProgressBar;

/**
 * 対戦時のカードを配布するシーンのクラス
 *
 */
public class BattleSceneDealCard implements BattleScene {
	
	private BattleActivity activity = null;
	
	
	// 山札の表示開始位置を定義
	private final int[][] initialCardsPos = new int[2][4];
/*		{
			{50,225,5,350},	// 自分
			{50,125,5, 20}	// 相手
		};
*/	
	// 縦幅を画面サイズに合わせる処理
	private void setInitialCardsPos(){
		this.initialCardsPos[0][0] = 50;
		this.initialCardsPos[0][1] = (int)this.activity.battleDisplay.getBBLayoutPosY()/4*2;
		this.initialCardsPos[0][2] = 5;
		this.initialCardsPos[0][3] = (int)this.activity.battleDisplay.getBBLayoutPosY()/4*3;
		this.initialCardsPos[1][0] = 50;
		this.initialCardsPos[1][1] = (int)this.activity.battleDisplay.getBBLayoutPosY()/4*1;
		this.initialCardsPos[1][2] = 5;
		this.initialCardsPos[1][3] = 10;
		
	}
	
	// 手札の左右のマージン
	private final int leftMargin = 5;
	
	// プログレスバー保持
	private ProgressBar myProgressBar = null;
	private ProgressBar enemyProgressBar = null;
	
	
	/**
	 * コンストラクタ
	 * @param activity 対戦画面のアクティビティ
	 */
	public BattleSceneDealCard(BattleActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void init() {
		this.setInitialCardsPos();
		// 相手情報（Name）
		((TextView)this.activity.findViewById(R.id.battle_enemyName)).setText("対戦相手 : " + this.activity.enemyPlayer.getName());

		// 相手情報（LP）
//		((TextView)this.activity.findViewById(R.id.battle_enemyLp)).setText("" + this.activity.enemyPlayer.getLifepoint());
		if (this.enemyProgressBar == null) {
			this.enemyProgressBar = (ProgressBar)this.activity.findViewById(R.id.battle_enemyLifebar);
			this.enemyProgressBar.setMax(this.activity.enemyPlayer.getLifepoint());
			this.enemyProgressBar.setProgress(this.activity.enemyPlayer.getLifepoint());
			
			((TextView)this.activity.findViewById(R.id.battle_enemyLp)).setText(" " + this.activity.enemyPlayer.getLifepoint());
			}

		// ユーザ情報（LP）
//		((TextView)this.activity.findViewById(R.id.battle_myLp)).setText("" + this.activity.myPlayer.getLifepoint());
		// ユーザ情報（LPBar）
		if (myProgressBar == null) {
			myProgressBar = (ProgressBar)this.activity.findViewById(R.id.battle_myLifebar);
			myProgressBar.setMax(this.activity.myPlayer.getLifepoint());
			myProgressBar.setProgress(this.activity.myPlayer.getLifepoint());
			
			((TextView)this.activity.findViewById(R.id.battle_enemyLp)).setText(" " + this.activity.myPlayer.getLifepoint());
		}


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
//			this.activity.myPlayer.cardInfo.shuffle();
//			this.activity.enemyPlayer.cardInfo.shuffle();
			
			// 配布予定カードのindexを初期化する
//			this.activity.myInfo.curPos = 0;
//			this.activity.enemyInfo.curPos = 0;

		}
		
		
		// 山札を表示する
		// プレイヤ分
		// 相手分
		this.displayCards(0);
		
		this.displayCards(1);
		
		this.startLimitDealCard();
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
				viewCards.get(i).setPosXY((this.leftMargin + myCardMarginX*(i)) , initialCardsPos[0][3]);
				viewCards.get(i).flippedCardFace();
			}else{
				viewCards.get(i).setPosXY((this.leftMargin + myCardMarginX*(i)) , initialCardsPos[1][3]);
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
	//スレッドでのオートプッシュ用
	Button button;
	
	public void setDealButton() {

		Button button = new Button(this.activity);
		//スレッドでのオートプッシュ用
		this.button = button;
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopLimitDealCard();
				dealCardsOnClick(v);
//				activity.finishOnClick(v);
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

	/**
	 * 配るボタン押下時に呼ばれる
	 * @param v
	 */
	public void dealCardsOnClick(View v){
		
		// ボタンを表示から消す
		this.activity.baseLayout.removeView(v);
		
		// １ターン目は５枚配布、それ以降は３枚配布
		int num = 0;
		int cardcount = this.activity.myPlayer.cardInfo.getUnUsedCardCount();
		if(cardcount == 30){
			num = 5;
		}else if(cardcount >= 3){
			num = 3;
		}else{
			num = cardcount;
		}
		
		// 相手のカードを配る
		this.dealEnemyCards(num);
		
		// 自分のカードを配る
		this.dealCards(num);

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
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i) , initialCardsPos[1][3], 3);
						}else{
							// １枚～２枚配り
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i - (init - 2)) , initialCardsPos[1][3], 3);
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
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i) , initialCardsPos[0][3], 3);
						}else{
							// １枚～２枚配り
							cardList[i - init].startMovingCard(leftMargin + myCardMarginX*(i - (init - 2)) , initialCardsPos[0][3], 3);
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
							
							// カードをひっくり返す効果音
							activity.playEffect(R.raw.card_open);

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


	/**
	 * カード配布時間制限
	 * ※約２秒
	 */
	
	//プッシュボタン押下判断 true:押されていない false:押された
	private boolean limitFlg = true;
	
	public void startLimitDealCard(){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				int limit = 0;
				limitFlg = true;
				while(limit <= 2000 && limitFlg == true){
					try {
						Thread.sleep(1);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					limit++;
				}
				// pushされていない場合処理を行う
				// かつonDestroyが行われていない場合処理を行う
				if((limitFlg)
				 &&(activity.finishFlg)){
					mHandler.post(new Runnable() {
						public void run() {
							dealCardsOnClick(button);
						}
					});
				}
			}
		}).start();
	}
	
	/**
	 * カード配布時間制限解除
	 */
	
	public void stopLimitDealCard(){
		this.limitFlg = false;
	}
	
	
}
