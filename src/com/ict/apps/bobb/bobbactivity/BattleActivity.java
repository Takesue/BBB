package com.ict.apps.bobb.bobbactivity;


import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.battle.BattleManager;
import com.ict.apps.bobb.battle.BattleScene;
import com.ict.apps.bobb.battle.BattleSceneBattleAnimation;
import com.ict.apps.bobb.battle.BattleSceneCardSelection;
import com.ict.apps.bobb.battle.BattleSceneDealCard;
import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.battle.SpecialCardInfo;
import com.ict.apps.bobb.battle.cpu.CPU01;
import com.ict.apps.bobb.battle.cpu.OnlinePlayer;
import com.ict.apps.bobb.battle.cpu.Player;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.SpecialCard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class BattleActivity extends BaseActivity{

	// 最背面のベースとなるLayout
	public BattleLayout baseLayout;


	// 現在のシーンのインデックスを保持
	private int currentScene = 0;
	
	// シーン毎にアクションの挙動を変える（場面転換）
	private BattleScene[] scenes = {
			new BattleSceneDealCard(this),
			new BattleSceneCardSelection(this),
			new BattleSceneBattleAnimation(this)
	};
	
	public boolean dealflg = false;
	
	// 対戦の采配を実施する
	public BattleManager bm = null;

	// 使用している特殊カードを保持
//	public int mySpinnerId = 0;
//	public int enemySpinnerId = 0;

	/**
	 * シーン設定
	 * @param currentScene 0:配るシーン 1: カード選択シーン 2:対戦シーン
	 */
	private void setCurrentScene(int currentScene) {
		this.currentScene = currentScene;
	}

	/**
	 * 次のシーンに変更
	 */
	public void changeNextScene() {
		// 終了するシーンの終了処理を呼び出す
		this.scenes[this.currentScene].finish();
		
		// 現在のシーンIndexに+1してシーン数で割った余りがシーン番号
		int sceneNum = (this.currentScene + 1) % this.scenes.length;
		
		// シーンを切り替える
		this.setCurrentScene(sceneNum);
		
		// シーンの初期化処理
		this.scenes[this.currentScene].init();
		
	}
	
	public BattleScene getCurrentScene() {
		return this.scenes[this.currentScene];
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_battle);
		
		// 戦闘画面のベース部品を取得
		this.baseLayout = (BattleLayout)this.findViewById(R.id.battle_base_layout);

		this.initBattleAct();
//		this.scenes[this.currentScene].init();
		this.bm = new BattleManager(this);
		
		// 対戦開始
		this.bm.startBattleScene();
	}
	

	/**
	 * 配るボタン押下時に呼ばれる
	 * @param v
	 */
	public void finishOnClick(View v){
		
		// ボタンを表示から消す
		this.baseLayout.removeView(v);
		
		// １ターン目は５枚配布、それ以降は３枚配布
		int num = 0;
		int cardcount = this.myInfo.getUnUsedCardCount();
		if(cardcount == 30){
			num = 5;
		}else if(cardcount >= 3){
			num = 3;
		}else{
			num = cardcount;
		}
		
		
		// 相手のカードを配る
		((BattleSceneDealCard)this.scenes[this.currentScene]).dealEnemyCards(num);
		
		// 自分のカードを配る
		((BattleSceneDealCard)this.scenes[this.currentScene]).dealCards(num);

	}
	

	/**
	 * カードオブジェクトのタッチイベントがきた場合に呼ばれる
	 * @param view
	 * @param action 0:up 1:down
	 */
	public void moveCard(BattleCardView view, int action) {
		
		this.scenes[this.currentScene].moveCard(view, action);
		
	}
	
	/**
	 * カードオブジェクトが長押しされた場合に呼ばれる
	 * @param view
	 */
	public void onLongClickCard(BattleCardView view) {
		
		// シーン側で長押し時の処理を実装する
		this.scenes[this.currentScene].onLongClickCard(view);
	}
	
	/**
	 * カードオブジェクトから手を離す場合に呼ばれる
	 * @param view
	 */
	public void actionUpCard(BattleCardView view) {
		
		// シーン側で長押し時の処理を実装する
		this.scenes[this.currentScene].actionUpCard(view);
		
	}
	// ユーザの対戦時ステータスを一元保持
	public CardBattlerInfo myInfo = null;
	// ユーザの対戦時特殊カードを一元保持
	public SpecialCardInfo mySpecialInfo = null;
	// 対戦相手の対戦時ステータスを一元保持
	public CardBattlerInfo enemyInfo = null;
	// 対戦相手の対戦時特殊カードを一元保持
	public SpecialCardInfo enemySpecialInfo = null;

	public Player enemyPlayer = null;

	/**
	 * 対戦画面アクティビティの初期処理
	 */
	public void initBattleAct() {
		
		
		// ユーザの対戦時情報を管理する管理テーブルに設定する
		this.myInfo = new CardBattlerInfo();
		this.myInfo.setName("まつこDX");
		this.myInfo.setLifepoint(4000);
		
		this.mySpecialInfo = new SpecialCardInfo();
		
		// 戦闘時使用キットクラスの使用例
		BeetleKit beetlekit1 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK1);
		BeetleKit beetlekit2 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK2);
		BeetleKit beetlekit3 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK3);
		BeetleKit beetlekit4 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK4);
		BeetleKit beetlekit5 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK5);
		BeetleKit specialkit1 = BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD1);
		BeetleKit specialkit2 = BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD2);
		BeetleKit specialkit3 = BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD3);

		this.setCardInfoToCardBattlerInfo(beetlekit1, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit2, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit3, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit4, this.myInfo);
		this.setCardInfoToCardBattlerInfo(beetlekit5, this.myInfo);
		this.setCardInfoToCardSpecialInfo(specialkit1, this.mySpecialInfo);
		this.setCardInfoToCardSpecialInfo(specialkit2, this.mySpecialInfo);
		this.setCardInfoToCardSpecialInfo(specialkit3, this.mySpecialInfo);
		
		
		
		Intent intent = this.getIntent();
		if ("online".equals(intent.getStringExtra("user_mode"))) {
			this.enemyPlayer = new OnlinePlayer(this);
		}
		else {
			// 対戦相手がCPUの場合
			this.enemyPlayer = new CPU01(this);
		}
		this.enemyInfo = this.enemyPlayer.createCardBattlerInfo();
		this.enemySpecialInfo = new SpecialCardInfo();
		this.setCardInfoToCardSpecialInfo(specialkit1, this.enemySpecialInfo);
		this.setCardInfoToCardSpecialInfo(specialkit2, this.enemySpecialInfo);
		this.setCardInfoToCardSpecialInfo(specialkit3, this.enemySpecialInfo);


	}

	/**
	 * 対戦デッキに設定されている虫キット情報を元に、６枚のカードを、管理テーブルに設定する
	 * @param beetlekit
	 */
	private void setCardInfoToCardBattlerInfo(BeetleKit beetlekit, CardBattlerInfo info) {
		// 取得した虫キットからカード生成
		BeetleCard[] cards = (BeetleCard[])beetlekit.createBeetleCards();
		
		BeetleCard[] sp = (BeetleCard[])beetlekit.createBeetleCards();
		
		
		for (int i = 0; i < cards.length; i++) {
			
			Integer ii = cards[i].getType();
			Log.d("cardslength", ii.toString());
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			
			viewCard.setControlActivity(this);
			
			// 自分の札だけクリックが利くようにする。
			if (this.myInfo.equals(info)) {
				// カードを長押しした場合のイベントリスナ
				viewCard.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						
						// ボタン長押
						onLongClickCard((BattleCardView)v);
						return true;
					}
				});
			}
			
			viewCard.setBeetleCard(cards[i]);
			
			info.setBattleCards(viewCard);
			
		}
	}
	
	/**
	 * 対戦デッキに設定されている特殊虫キット情報を元にカードを、管理テーブルに設定する
	 * @param specialkit
	 */
	private void setCardInfoToCardSpecialInfo(BeetleKit beetlekit, SpecialCardInfo info) {
		// 取得した特殊虫キットからカード生成
		SpecialCard[] cards = (SpecialCard[])beetlekit.createBeetleCards();
		
		SpecialCard[] sp = (SpecialCard[])beetlekit.createBeetleCards();
		
		
		for (int i = 0; i < cards.length; i++) {
			
			Integer ii = cards[i].getType();
			Log.d("cardslength", ii.toString());
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			
			viewCard.setControlActivity(this);
			
			// 自分の札だけクリックが利くようにする。
			if (this.myInfo.equals(info)) {
				// カードを長押しした場合のイベントリスナ
				viewCard.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						
						// ボタン長押
						onLongClickCard((BattleCardView)v);
						return true;
					}
				});
			}
			
			viewCard.setSpecialCard(cards[i]);
			
			info.setSpecialCards(viewCard);
			
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
