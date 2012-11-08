package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleLayout;
import com.ict.apps.bobb.bobbactivity.BattleCards;
import com.ict.apps.bobb.bobbactivity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 対戦時のカードを配布するシーンのクラス
 *
 */
public class BattleSceneDealCard implements BattleScene {
	
	private BattleActivity activity = null;
	
	// 山札の表示開始位置を定義
	private final int[][] initialCardsPos = 
		{
			{50,225},	// 自分
			{50,125}	// 相手
		};
	
	// 山札の表示開始位置を定義
	private final int[][] myCardsPos = 
		{
			{5, 380},	// 自分1
			{65, 380},	// 自分2
			{125, 380},	// 自分3
			{185, 380},	// 自分4
			{245, 380},	// 自分5
		};
	
	/**
	 * コンストラクタ
	 * @param activity 対戦画面のアクティビティ
	 */
	public BattleSceneDealCard(BattleActivity activity) {
		this.activity = activity;
	}

	@Override
	public void init() {
		
		// 山札を表示する
		// プレイヤ分
		// 相手分
		this.displayCards(0);
		
		this.displayCards(1);
		
		
	}

	@Override
	public void moveCard(View view, int action) {
		
		
	}

	@Override
	public void finish() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	public void displayCards(int type) {
		
		ArrayList<BattleCards> viewCards = null;
		// 自分のカードを全部取得
		if (type == 0) {
			viewCards = this.activity.myInfo.getAllCards();
		}
		else {
			viewCards = this.activity.enemyInfo.getAllCards();
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
			viewCards.get(i).setPosXY((int)((this.initialCardsPos[type][0] + (5*i)) *tmpDensity), (int)(this.initialCardsPos[type][1]*tmpDensity));
			
			viewCards.get(i).flippedCardBack();
			
			// 戦闘ベース部品にcard追加する
			this.activity.vgroup.addView(viewCards.get(i), cartParams);
		}
		
	}
	
	public void dealDards(int num) {
		
		final int count = num;
		
		// スレッド起動
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					BattleCards[] cardList = new BattleCards[count];
						for (int i=0;i < count; i++) {
							cardList[i] = activity.myInfo.getNextCard();
							cardList[i].startMovingCard(myCardsPos[i][0], myCardsPos[i][1]);
							Thread.sleep(200);
						}
						
						boolean flag = true;
						while(flag) {
							
							// 配り終わり待ちループ
							boolean cflag = false;
							for (int i=0; i < count; i++) {
								if (cardList[i].moveFlag == true) {
									cflag = true;
								}
							}
							
							if (!cflag) {
								flag = false;
								for (int i=0; i < count; i++) {
									cardList[i].flippedCardFace();
								}
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


}
