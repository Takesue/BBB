package com.ict.apps.bobb.bobbactivity;


import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.battle.BattleManager;
import com.ict.apps.bobb.battle.BattleScene;
import com.ict.apps.bobb.battle.BattleSceneBattleAnimation;
import com.ict.apps.bobb.battle.BattleSceneCardSelection;
import com.ict.apps.bobb.battle.BattleSceneDealCard;

import com.ict.apps.bobb.battle.CardInfo;
import com.ict.apps.bobb.battle.SpecialCardInfo;
import com.ict.apps.bobb.battle.player.CPU01;
import com.ict.apps.bobb.battle.player.MyPlayer;
import com.ict.apps.bobb.battle.player.OnlinePlayer;
import com.ict.apps.bobb.battle.player.Player;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.StatusInfo;
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
import android.widget.LinearLayout;

public class BattleActivity extends BaseActivity{

	// 最背面のベースとなるLayout
	public BattleLayout baseLayout;


	// 現在のシーンのインデックスを保持
	private int currentScene = 0;
	
	// シーン毎にアクションの挙動を変える（場面転換）
	private BattleScene[] scenes = null;
	
	public boolean dealflg = false;
	
	// 対戦の采配を実施する
	public BattleManager bm = null;
	
	// ユーザの対戦時情報を一元保持
	public Player myPlayer = null;
	
	// 対戦相手の対戦時情報を一元保持
	public Player enemyPlayer = null;
	

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
		
//		// 戦闘画面のベース部品を取得
//		this.baseLayout = (BattleLayout)this.findViewById(R.id.battle_base_layout);
//
//		this.bm = new BattleManager(this);
//		
//		// 対戦開始前の初期処理
//		this.bm.initBattleAct();
//		
//		// 対戦開始
//		this.bm.startBattleScene();
		this.gameStart();
	}
	
	/**
	 * 対戦ゲーム開始
	 */
	public void gameStart() {
		
		/*------------------------
		 *  初期化処理
		 -------------------------*/
		this.currentScene = 0;
		this.dealflg = false;
		this.bm = null;
		this.myPlayer = null;
		this.enemyPlayer = null;
		
		BattleScene[] scenes = {
				new BattleSceneDealCard(this),
				new BattleSceneCardSelection(this),
				new BattleSceneBattleAnimation(this)
		};
		this.scenes = scenes;
		
		// 戦闘画面のベース部品を取得
		this.baseLayout = (BattleLayout)this.findViewById(R.id.battle_base_layout);
/*		float tmpDensity = this.baseLayout.getResources().getDisplayMetrics().density;
		int myCardMarginX = (int) ((new Float(this.baseLayout.getWidth())/tmpDensity));
		int myCardMarginY = (int) ((new Float(this.baseLayout.getHeight())/tmpDensity));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)((myCardMarginX) *tmpDensity), (int)((myCardMarginY) *tmpDensity));
		this.findViewById(R.id.battle_base_layout).setLayoutParams(params);
*/
		// 対戦管理インスタンス生成
		this.bm = new BattleManager(this);
		
		// 対戦開始前の初期処理
		this.bm.initBattleAct();
		
		// 対戦開始
		this.bm.startBattleScene();

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
	
	@Override
	protected void onDestroy() {
		
		if (this.getCurrentScene() instanceof BattleSceneCardSelection) {
			((BattleSceneCardSelection)this.getCurrentScene()).stopLimitTimeCountDown();
		}
		super.onDestroy();
	}

}
