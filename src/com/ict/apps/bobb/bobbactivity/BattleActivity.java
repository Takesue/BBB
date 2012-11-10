package com.ict.apps.bobb.bobbactivity;


import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.battle.BattleScene;
import com.ict.apps.bobb.battle.BattleSceneBattleAnimation;
import com.ict.apps.bobb.battle.BattleSceneCardSelection;
import com.ict.apps.bobb.battle.BattleSceneDealCard;
import com.ict.apps.bobb.battle.CardBattlerInfo;
import com.ict.apps.bobb.battle.cpu.CPU01;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_battle);
		
		// 戦闘画面のベース部品を取得
		this.baseLayout = (BattleLayout)this.findViewById(R.id.battle_base_layout);

		this.initBattleAct();
		this.scenes[this.currentScene].init();
		
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
	// 対戦相手の対戦時ステータスを一元保持
	public CardBattlerInfo enemyInfo = null;

	public CPU01 cpu = null;

	/**
	 * 対戦画面アクティビティの初期処理
	 */
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
		this.cpu = new CPU01();
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// 静的情報であるため、対戦画面終了時にクリアする。
		BattleSceneCardSelection.threeCardselected = false;;
	}

}
