package com.ict.apps.bobb.bobbactivity;


import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.battle.BattleScene;
import com.ict.apps.bobb.battle.BattleSceneDealCard;
import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.battle.cpu.CPU01;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.os.Handler;

public class BattleActivity extends Activity{

	int cardCount = 30;
	int maxTime = 5;
	
	private BattleActivity bAct;

	public BattleLayout vgroup;

	// カードの表示部品（card.xml）
	private BattleCards myViewCard[]    = new BattleCards[this.cardCount];
	private BattleCards rivalViewCard[] = new BattleCards[this.cardCount];

	// カード詳細表示部品(carddetail.xml)
	private View myViewCardDerail = null;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_battle);
//        Log.d("Create","start");
//        this.getThis();
//        Log.d("Create","1");
//		for(int i = this.cardCount - 1; i >= 0; i--){
//			this.displayRivalCards((50+(i*5)), 125, i);
//		}
//		for(int i = this.cardCount - 1; i >= 0; i--){
//			this.displayMyCards((50+((this.cardCount-i)*5)), 225, i, false);
//		}
//		// 次回山札表示時３枚減らすため
//		this.cardCount = this.cardCount - 3;
//	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle);
		
		// 戦闘画面のベース部品を取得
		this.vgroup = (BattleLayout)this.findViewById(R.id.battle_base_layout);

		this.initBattleAct();
		this.scenes[0].init();
		
	}
	

    public void getThis(){
    	this.bAct = this;
    }
//    public void finishOnClick(View v){
    public void onClickButton(View v){
    	
		finish();
    }
    
	/**
	 * 手札カードを表示する
	 * @param left
	 * @param top
	 */
	public void displayRivalCards(int left, int top, int ix) {
		
		// 相手CARD用View取得
		View viewCard = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.my_cards, null);
		// カードビュークラスにActivityを渡す
		((BattleCards) viewCard).setControlActivity(this);
		// カードインスタンスを変数として保持する
		this.rivalViewCard[ix] = (BattleCards)viewCard;
		
		// Densityの値を取得
		float tmpDensity = this.getResources().getDisplayMetrics().density;
		
		BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		
		// 戦闘画面のベース部品を取得
		BattleLayout vgroup = (BattleLayout)this.findViewById(R.id.battle_base_layout);

		// 戦闘ベース部品にcard追加する
		vgroup.addView(viewCard, cartParams);
		
	}

	public void displayMyCards(int left, int top, int ix, boolean onClick) {
		
		// CARD用View取得
		View viewCard = null;
		if(onClick == true){
			viewCard = ((LayoutInflater)
					getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.cardbattle_detail, null);
		}else{
			viewCard = ((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
		}
		// カードビュークラスにActivityを渡す
		((BattleCards) viewCard).setControlActivity(this);
		// カードインスタンスを変数として保持する
		this.myViewCard[ix] = (BattleCards)viewCard;
		// カードを長押しした場合のイベントリスナ
		if(onClick == true){
			viewCard.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					
					// ボタン長押しでカード詳細画面を表示
					viewDetailCards(40, 10);
					
					return false;
				}
			});
		}
		// Densityの値を取得
		float tmpDensity = this.getResources().getDisplayMetrics().density;
		
		BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		
		// 戦闘画面のベース部品を取得
		BattleLayout vgroup = (BattleLayout)this.findViewById(R.id.battle_base_layout);

		// 戦闘ベース部品にcard追加する
		vgroup.addView(viewCard, cartParams);
		
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
		this.vgroup = (BattleLayout)this.findViewById(R.id.battle_base_layout);

		// CARD用View取得
		this.myViewCardDerail = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.cardbattle_detail, null);
		
		BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
				BattleLayout.LayoutParams.WRAP_CONTENT,
				BattleLayout.LayoutParams.WRAP_CONTENT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		

		// 戦闘ベース部品にカード詳細を追加する
		this.vgroup.addView(this.myViewCardDerail, cartParams);
		
	}
	
	/**
	 * カード詳細を消す
	 */
	public void invisibleCardDetail() {
		
		if (vgroup == null) {
			return;
		}
		// 親グループからカード詳細を外す
		this.vgroup.removeView(this.myViewCardDerail);
		// 画面再描画を要求
		this.vgroup.invalidate();
	}

	// カードが移動するアニメーションを作ってみる　利用したのは、ハンドラーの遅延でスレッド仕込むやつ。
	
	/**
	 * 配るボタン押下時に呼ばれる
	 * @param v
	 */
//	public void onClickButton(View v) {
	public void finishOnClick(View v){
		
		((BattleSceneDealCard)this.scenes[0]).dealDards(5);

	}
	
	/**
	 * 山札を消す
	 */
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
//	private Runnable setFinishCard = new Runnable() {
//		public void run() {
	public void setFinishCard(){
		// 戦闘画面のベース部品を取得
		BattleLayout vgroup = (BattleLayout)findViewById(R.id.battle_base_layout);
		
		// 山札として残っているカードを削除
		for(int i = cardCount + 2; i >= 5; i--){
			Log.d("F","2");
			myViewCard[i].startDeleteCard(vgroup);
			rivalViewCard[i].startDeleteCard(vgroup);
		}
		// ユーザー手札を削除し、表面を表示させる
		for(Integer i = 0; i <= 4; i++){
			myViewCard[i].startDeleteCard(vgroup);
		}
	}
		
		
		
		
/*				// 名前設定
				((TextView)myViewCard[i].findViewById(R.id.carddetail_name)).setText("name");
				((TextView)myViewCard[i].findViewById(R.id.carddetail_name)).setTextSize(5.0f);
				// 説明設定
				((TextView)myViewCard[i].findViewById(R.id.carddetail_atk)).setText("攻：" + "1000");
				((TextView)myViewCard[i].findViewById(R.id.carddetail_atk)).setTextSize(5.0f);
				// 説明設定
				((TextView)myViewCard[i].findViewById(R.id.carddetail_def)).setText("守：" + "300");
				((TextView)myViewCard[i].findViewById(R.id.carddetail_def)).setTextSize(5.0f);
				// 説明設定
				((TextView)myViewCard[i].findViewById(R.id.carddetail_intoro)).setText("説明：" + "せつめいと読みます");
				((TextView)myViewCard[i].findViewById(R.id.carddetail_intoro)).setTextSize(5.0f);
				// 画像設定
				((ImageView)myViewCard[i].findViewById(R.id.carddetail_icon)).setImageResource(R.drawable.beetle1);
				BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
					(int)50,
					(int)50;
				// 画像設定
				((ImageView)myViewCard[i].findViewById(R.id.carddetail_attrribute)).setImageResource(R.drawable.wind);
				BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
					(int)50,
					(int)50;
*/				
	
	// ハンドラーを取得
	private Handler mHandler = new Handler();
	private int posLeft = 0;
	private int posTop = 0;
	private int posIx = 0;
	private BattleActivity bA = this;
	
//	public void displayMyCards1(int left, int top, int ix, boolean onClick) {
		
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
	private Runnable mDisplayTimeTask = new Runnable() {
		public void run() {
			// CARD用View取得
			View viewCard = null;
			viewCard = ((LayoutInflater)
						getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
						R.layout.cardbattle_detail, null);
			// カードビュークラスにActivityを渡す
			((BattleCards) viewCard).setControlActivity(bA);
			// カードインスタンスを変数として保持する
			myViewCard[posIx] = (BattleCards)viewCard;
			// カードを長押しした場合のイベントリスナ
			viewCard.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					
					// ボタン長押しでカード詳細画面を表示
					viewDetailCards(40, 10);
					
					return false;
				}
			});
			// Densityの値を取得
			float tmpDensity = getResources().getDisplayMetrics().density;
			
			BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
					(int)(getResources().getDimensionPixelSize(R.dimen.card_width)),
					(int)(getResources().getDimensionPixelSize(R.dimen.card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)(posLeft*tmpDensity), (int)(posTop*tmpDensity), 0, 0);
			
			// 戦闘画面のベース部品を取得
			BattleLayout vgroup = (BattleLayout)findViewById(R.id.battle_base_layout);
	
			// 戦闘ベース部品にcard追加する
			vgroup.addView(viewCard, cartParams);
		}
	};

	// 現在のシーンのインデックスを保持
	private int currentScene = 0;
	// シーン毎にアクションの挙動を変える（場面転換）
	private BattleScene[] scenes = {
			new BattleSceneDealCard(this)
	};
	
	/**
	 * カードオブジェクトのタッチイベントがきた場合に呼ばれる
	 * @param view
	 * @param action 0:up 1:down
	 */
	public void moveCard(View view, int action) {
		
		this.scenes[this.currentScene].moveCard(view, action);
		
	}
	
	/**
	 * カードオブジェクトが長押しされた場合に呼ばれる
	 * @param view
	 */
	public void onLongClickCard(View view) {
		
		
	}
	
	// ユーザの対戦時ステータスを一元保持
	public CardBattlerInfo myInfo = null;
	// 対戦相手の対戦時ステータスを一元保持
	public CardBattlerInfo enemyInfo = null;
	
	public void initBattleAct() {
		
		// ユーザの対戦時情報を管理する管理テーブルに設定する
		this.myInfo = new CardBattlerInfo();
		this.myInfo.setName("まつこDX");
		this.myInfo.setLifepoint(4000);
		
		// 戦闘時使用キットクラスの使用例
		BeetleKit beetlekit1 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK1);
		BeetleKit beetlekit2 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK2);
		BeetleKit beetlekit3 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK3);
		BeetleKit beetlekit4 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK4);
		BeetleKit beetlekit5 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK5);

		this.setCardInfoToCardBattlerInfo(beetlekit1, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit2, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit3, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit4, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit5, this.myInfo);
		
		
		// 対戦相手がCPUの場合
		this.enemyInfo = new CardBattlerInfo();
		this.enemyInfo.setName("CPU01");
		this.enemyInfo.setLifepoint(4000);
		
		// CPUの使用する使用する虫キットを取得する
		// カードを管理テーブルに設定する。
		this.setCardInfoToCardBattlerInfo(beetlekit1, this.enemyInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit2, this.enemyInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit3, this.enemyInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit4, this.enemyInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit5, this.enemyInfo);

	}

	/**
	 * 対戦デッキに設定されている虫キット情報を元に、６枚のカードを、管理テーブルに設定する
	 * @param beetlekit
	 */
	private void setCardInfoToCardBattlerInfo(BeetleKit beetlekit, CardBattlerInfo info) {
		// 取得した虫キットからカード生成
		BeetleCard[] cards = (BeetleCard[])beetlekit.createBeetleCards();
		
		for (int i = 0; i < cards.length; i++) {
			
			BattleCards viewCard = (BattleCards)((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			
			viewCard.setControlActivity(this);
			// カードを長押しした場合のイベントリスナ
			viewCard.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					
					// ボタン長押しでカード詳細画面を表示
					viewDetailCards(40, 10);
					
					return false;
				}
			});
			
			viewCard.setBeetleCard(cards[i]);
			
			info.setBattleCards(viewCard);
			
		}
	}
	
}
