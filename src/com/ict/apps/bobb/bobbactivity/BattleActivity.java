package com.ict.apps.bobb.bobbactivity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class BattleActivity extends Activity{

	int cardCount = 30;
	int maxTime = 5;
	
	private BattleActivity bAct;

	private BattleCardDetail vgroup;

	// カードの表示部品（card.xml）
	private BattleCards myViewCard[]    = new BattleCards[this.cardCount];
	private BattleCards rivalViewCard[] = new BattleCards[this.cardCount];

	// カード詳細表示部品(carddetail.xml)
	private View myViewCardDerail = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        Log.d("Create","start");
        this.getThis();
        Log.d("Create","1");
		for(int i = this.cardCount - 1; i >= 0; i--){
			this.displayRivalCards((50+(i*5)), 125, i);
		}
		for(int i = this.cardCount - 1; i >= 0; i--){
			this.displayMyCards((50+((this.cardCount-i)*5)), 225, i, false);
		}
		// 次回山札表示時３枚減らすため
		this.cardCount = this.cardCount - 3;
	}
	
    public void getThis(){
    	this.bAct = this;
    }
    public void finishOnClick(View v){
    	
		finish();
    }
    
	/**
	 * 手札カードを表示する
	 * @param left
	 * @param top
	 */
	public void displayRivalCards(int left, int top, int ix) {
		
        Log.d("dR","1");
		// 相手CARD用View取得
		View viewCard = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.my_cards, null);
        Log.d("dR","2");
		// カードビュークラスにActivityを渡す
		((BattleCards) viewCard).setControlActivity(this);
        Log.d("dR","3");
		// カードインスタンスを変数として保持する
		this.rivalViewCard[ix] = (BattleCards)viewCard;
		
        Log.d("dR","4");
		// Densityの値を取得
		float tmpDensity = this.getResources().getDisplayMetrics().density;
		
        Log.d("dR","5");
		BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		
		// 戦闘画面のベース部品を取得
		BattleCardDetail vgroup = (BattleCardDetail)this.findViewById(R.id.battle_base_layout);

		// 戦闘ベース部品にcard追加する
		vgroup.addView(viewCard, cartParams);
		
	}

	public void displayMyCards(int left, int top, int ix, boolean onClick) {
		
		// 相手CARD用View取得
		View viewCard = null;
		if(onClick == true){
			viewCard = ((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.card_detailview, null);
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
		
		BattleCardDetail.LayoutParams cartParams = new BattleCardDetail.LayoutParams(
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_width)),
				(int)(this.getResources().getDimensionPixelSize(R.dimen.card_height)));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		
		// 戦闘画面のベース部品を取得
		BattleCardDetail vgroup = (BattleCardDetail)this.findViewById(R.id.battle_base_layout);

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
	public void onClickButton(View v) {
		
		// スレッド起動
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					// 5枚移動する的なやつにする。
					myViewCard[0].startMovingCard(30, 10, 2, 380);
					rivalViewCard[0].startMovingCard(30, 10, 2, 380);
					Thread.sleep(200);
					myViewCard[1].startMovingCard(30, 10, 2, 380);
					rivalViewCard[1].startMovingCard(30, 10, 2, 380);
					Thread.sleep(200);
					myViewCard[2].startMovingCard(30, 10, 2, 380);
					rivalViewCard[2].startMovingCard(30, 10, 2, 380);
					Thread.sleep(200);
					myViewCard[3].startMovingCard(30, 10, 2, 380);
					rivalViewCard[3].startMovingCard(30, 10, 2, 380);
					Thread.sleep(200);
					myViewCard[4].startMovingCard(30, 10, 2, 380);
					rivalViewCard[4].startMovingCard(30, 10, 2, 380);
					Thread.sleep(200);
					setFinishCard();
				} catch (InterruptedException e) {
				}
			}
		}).start();

	}
	
	/**
	 * 山札を消す
	 */
	// 定期的に呼び出されるためのRunnnableのインナークラス定義
//	private Runnable setFinishCard = new Runnable() {
//		public void run() {
	public void setFinishCard(){
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
			this.displayMyCards(i*65,380,i,true);
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

	
}
