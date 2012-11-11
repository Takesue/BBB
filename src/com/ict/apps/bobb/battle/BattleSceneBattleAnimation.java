package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import org.apache.http.entity.SerializableEntity;

import com.ict.apps.bobb.battle.cpu.CPU01;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.BattleLayout;
import com.ict.apps.bobb.bobbactivity.R;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BattleSceneBattleAnimation implements BattleScene {

	// 対戦画面アクティビティ
	private BattleActivity activity = null;


	// 相手の３つ選択して大きくなった状態のカードを生成して保持する。
	ArrayList<BattleCardView> bigCards = new ArrayList<BattleCardView>();

	// 相手の３つ選択して大きくなった状態のカードを生成して保持する。
	ArrayList<BattleCardView> bigEnemyCards = new ArrayList<BattleCardView>();

	// 相手の攻撃力
	int enemyAttack = 0;
	// 相手の守備力
	int enemyDefense = 0;
	// 自分の攻撃力
	int myAttack = 0;
	// 自分の守備力
	int myDefense = 0;
	
	
	/**
	 * コンストラクタ
	 * @param activity 対戦画面のアクティビティ
	 */
	public BattleSceneBattleAnimation(BattleActivity activity) {
		this.activity = activity;
	}

	@Override
	public void init() {
		// 画面に必要な情報を設定
		
		// 相手の選択したカード3枚
		ArrayList<BattleCardView> cards = this.getEnemySelectCards();
		// 相手の選択カードを拡大表示
		this.viewSelectedBigCardDisp(1, this.activity.enemyInfo);
		
		
		// 自分の選択カードを拡大表示
		this.viewSelectedBigCardDisp(0, this.activity.myInfo);
		
		// 相手の合計値表示
		LinearLayout enemyTotal = this.viewTotal(200, 180);
		this.calcAndViewTotal(1, cards, enemyTotal);
		
		// 自分の合計値表示
		LinearLayout myTotal = this.viewTotal(5, 180);
		this.calcAndViewTotal(0, this.activity.myInfo.getSelectedCard(), myTotal);
		
		// 合計値を消す
//		this.calcDelete(enemyTotal, myTotal);
		
		// ダメージをtoastで表示する
//		this.damegeMesege();
		
		// カードをアニメーションさせる
//		this.animationCards(1, this.activity.enemyInfo, enemyTotal);
//		this.animationCards(0, this.activity.myInfo, myTotal);
		
		// 各ライフポイントを削る
		this.lifePointRecalc();
		
		// カード使用済み
		this.battleAnimationDustCard();
		
		//次のシーンへ移る
		this.callChangeNexrScene();
	}

	@Override
	public void moveCard(BattleCardView view, int action) {
	}

	@Override
	public void finish() {
		// 表示しているビューを全て削除する
		this.activity.baseLayout.removeAllViews();
	}

	@Override
	public void onLongClickCard(BattleCardView view) {
	}

	@Override
	public void actionUpCard(BattleCardView view) {
	}

	/**
	 * 相手のカードを取得
	 */
	private ArrayList<BattleCardView> getEnemySelectCards() {
		return this.activity.cpu.getSelectCard(this.activity.myInfo, this.activity.enemyInfo);
	}
	
	/**
	 * 相手の特殊カードを取得
	 */
	private ArrayList<BattleCardView> getEnemySelectSpecialCards() {
		return this.activity.cpu.getSelectSpacialCard(this.activity.myInfo, this.activity.enemyInfo);
	}


	// ハンドラー取得
	private Handler mHandler = new Handler();

	/**
	 * シーン変更（別スレッド呼び出し用）
	 */
	private void callChangeNexrScene() {
		
//		this.activity.changeNextScene();
		// 別スレッドから呼ぶので、ハンドラーで実装する
		this.mHandler.post(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
					finish();
					activity.changeNextScene();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	
	}

	
	/**
	 * 選択カードの確認拡大表示
	 */
	private void viewSelectedBigCardDisp(int type, CardBattlerInfo info) {
		
		ArrayList<BattleCardView> bigCardList = null;
		
		if (type == 0) {
			bigCardList = this.bigCards;
		}
		else if (type ==1) {
			bigCardList = this.bigEnemyCards;
		}
		// 表示を一旦クリア
		bigCardList.clear();
		
		// 選択した3つを中央に表示
		// 多少拡大する
		ArrayList<BattleCardView> cards = info.getSelectedCard();
		
		
		int posX = 10;
		int posY = 320;
		if (type == 1) {
			posY = 30;
		}
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		int myCardMarginX = (int) ((new Float(this.activity.baseLayout.getWidth())/tmpDensity - (new Float(posX)*2))/3);
		
		int length = cards.size();
		for (int i = 0; i < length; i++) {
			// Densityの値を取得
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards_big, null);
			
			viewCard.setControlActivity(this.activity);

			BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.big_card_width)),
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.big_card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((posX + (myCardMarginX * i)) *tmpDensity), (int)(posY*tmpDensity), 0, 0);
			viewCard.setBeetleCard(cards.get(i).getCardInfo());
			viewCard.setUpFlag(true);
			viewCard.flippedCardFace();
			
			// 参照を保持する
			bigCardList.add(viewCard);
			
			// 戦闘ベース部品にcard追加する
			this.activity.baseLayout.addView(viewCard, 0, cartParams);
		}
	}

	/**
	 * 合計表示を画面に表示する
	 * @param left
	 * @param top
	 */
	public LinearLayout viewTotal(int left, int top) {
		
		// Densityの値を取得
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		

		// CARD用View取得
		LinearLayout totalView = (LinearLayout) ((LayoutInflater) this.activity.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.battle_totalpoint, null);
		
		BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
				(int)(100 * tmpDensity),
				(int)(120 * tmpDensity));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		
		// 攻撃力合計
		((TextView)totalView.findViewById(R.id.battle_total_attack)).setText("0");
		
		// 守備力合計
		((TextView)totalView.findViewById(R.id.battle_total_defense)).setText("0");
		
		
		// 戦闘ベース部品にカード詳細を追加する
		this.activity.baseLayout.addView(totalView, cartParams);
		
		
		return totalView;
	}

	/**
	 * 攻撃力と守備力の合計値を算出して表示する
	 * 
	 * @param cards
	 * @param view   合計を表示しているLayoutView
	 */
	private void calcAndViewTotal(int typeH, ArrayList<BattleCardView> cards, View view) {
		
		int totalAttack = 0;
		int totalDefense = 0;

		// 合計値加算
		for (BattleCardView card : cards) {
			int type = card.getCardInfo().getType();
			if (type == 3) {
				// 攻撃力加算
				totalAttack += card.getCardInfo().getAttack();
			}
			else if (type == 4) {
				// 守備力加算
				totalDefense += card.getCardInfo().getDefense();
			}
		}
		// 攻撃力合計
		((TextView)view.findViewById(R.id.battle_total_attack)).setText(Integer.toString(totalAttack));
		
		// 守備力合計
		((TextView)view.findViewById(R.id.battle_total_defense)).setText(Integer.toString(totalDefense));
		
		if(typeH == 0){
			this.myAttack = totalAttack;
			this.myDefense = totalDefense;
		}else{
			this.enemyAttack = totalAttack;
			this.enemyDefense = totalDefense;
		}
		
	}
	
	/**
	 * 合計表示を画面から消す
	 */
	public void calcDelete(LinearLayout enemyTotal, LinearLayout myTotal){
		final LinearLayout eT = enemyTotal;
		final LinearLayout mT = myTotal;
		// 別スレッドから呼ぶので、ハンドラーで実装する
		this.mHandler.post(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1500);
					activity.baseLayout.removeView(eT);
					activity.baseLayout.removeView(mT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 相手カード自分カードを上下に動かしてアニメーションさせる
	 */
	public void animationCards(int type, CardBattlerInfo info, LinearLayout totalDisp) {
		
		// 合計表示削除用
		final LinearLayout total = totalDisp;
		
		ArrayList<BattleCardView> bigCardList = null;
		if (type == 0) {
			bigCardList = this.bigCards;
		}else{
			bigCardList = this.bigEnemyCards;
		}
		final ArrayList<BattleCardView> cards = bigCardList;
		
		final CardBattlerInfo cBInfo = info;
		
		final int posX = 10;
		int num = 0;
		if(type == 0){
			num = 100;
		}else{
			num = 100;
		}
		final int stopPosY = num;
		
		if(type == 0){
			num = 120;
		}else{
			num = 80;
		}
		final int centerPosY = num;
		
		if(type == 0){
			num = 200;
		}else{
			num = 30;
		}
		final int startPosY = num;
		
		final int runType = type;
		
		// Densityの値を取得
		final float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		final int myCardMarginX = (int) ((new Float(this.activity.baseLayout.getWidth())/tmpDensity - (new Float(posX)*2))/3);
		
/*		// 別スレッドから呼ぶので、ハンドラーで実装する
		this.mHandler.post(new Runnable() {
			public void run() {
				
				try {
					if(runType == 0){
						Thread.sleep(500);
					}else{
						Thread.sleep(500);
					}
					// 合計表示削除
					activity.baseLayout.removeView(total);
					
					int length = cards.size();
					for (int i = 0; i < length; i++) {
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(startPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(stopPosY*tmpDensity), 3);
						
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(stopPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(centerPosY*tmpDensity), 5);
						
						Thread.sleep(100);
						
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(centerPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(stopPosY*tmpDensity), 5);
						
						Thread.sleep(100);
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(stopPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(centerPosY*tmpDensity), 5);
						
						Thread.sleep(100);
						
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(centerPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(startPosY*tmpDensity), 5);
					}
					
					// ダメージをtoastで表示
					int myAttack = getMyAttack();
					int enemyAttack = getEnemyAttack();
					Toast.makeText(activity, myAttack + "のダメージを与えた", 1000).show();
					Toast.makeText(activity, enemyAttack + "のダメージを与えられた", 1000).show();
					
					Thread.sleep(2000);
					// 次のシーンへ
					callChangeNexrScene();
					
				}
				catch (InterruptedException e) {
				}
			}
		});
*/
		// スレッド起動
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					if(runType == 0){
						Thread.sleep(1500);
					}else{
						Thread.sleep(1500);
					}
					
					int length = cards.size();
					for (int i = 0; i < length; i++) {
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(startPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(stopPosY*tmpDensity), 3);
						
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(stopPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(centerPosY*tmpDensity), 5);
						
						Thread.sleep(100);
						
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(centerPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(stopPosY*tmpDensity), 5);
						
						Thread.sleep(100);
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(stopPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(centerPosY*tmpDensity), 5);
						
						Thread.sleep(100);
						
						cards.get(i).setPosXY((int)((posX + (myCardMarginX * i))), (int)(centerPosY*tmpDensity));
						cards.get(i).startMovingCard((int)(posX + (myCardMarginX * i)), (int)(startPosY*tmpDensity), 5);
					}
					
				}
				catch (InterruptedException e) {
				}
			}
		}).start();

	}
	/**
	 * ダメージをtoastで表示する
	 */
	public void damegeMesege(){
		// 別スレッドから呼ぶので、ハンドラーで実装する
		this.mHandler.post(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000);
					int myAttack = getMyAttack();
					int enemyAttack = getEnemyAttack();
					Toast.makeText(activity, myAttack + "のダメージを与えた", 1000).show();
					Toast.makeText(activity, enemyAttack + "のダメージを与えられた", 1000).show();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * 各ライフポイントを削る
	 */
	public void lifePointRecalc(){
		int enemyLp = this.activity.enemyInfo.getLifepoint() - getMyAttack(); 
		int myLp = this.activity.myInfo.getLifepoint() - getEnemyAttack(); 
		this.activity.enemyInfo.setLifepoint(enemyLp);
		this.activity.myInfo.setLifepoint(myLp);
	}
	
	/**
	 * ダメージ計算
	 */
	public int getMyAttack(){
		int atack = 0;
		if((this.myAttack - this.enemyDefense) > 0){
			atack = this.myAttack - this.enemyDefense; 
		}else{
			atack = 0;
		}
		return atack;
	}
	public int getEnemyAttack(){
		int atack = 0;
		if((this.myAttack - this.enemyDefense) > 0){
			atack = this.enemyAttack - this.myDefense; 
		}else{
			atack = 0;
		}
		return atack;
	}
	
	/**
	 * カード使用済みカードを使用済みへ変更する
	 */
	public void battleAnimationDustCard(){
		for (BattleCardView card : this.activity.enemyInfo.getSelectedCard()) {
			this.activity.enemyInfo.dustCard(card);
		}
		for (BattleCardView card : this.activity.myInfo.getSelectedCard()) {
			this.activity.myInfo.dustCard(card);
		}
	}

}
