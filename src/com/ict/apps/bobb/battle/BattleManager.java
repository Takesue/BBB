package com.ict.apps.bobb.battle;

import com.ict.apps.bobb.battle.player.CPU01;
import com.ict.apps.bobb.battle.player.MyPlayer;
import com.ict.apps.bobb.battle.player.OnlinePlayer;
import com.ict.apps.bobb.battle.player.Player;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQueryBattleReqest;
import com.ict.apps.bobb.online.OnlineQueryBattleStatus;
import com.ict.apps.bobb.online.OnlineQueryEnemyUsingCard;
import com.ict.apps.bobb.online.OnlineQueryOnlineUserList;
import com.ict.apps.bobb.online.OnlineQueryRegistUsingCard;
import com.ict.apps.bobb.online.OnlineQueryResponseForBattleReq;

import android.content.Context;
import android.content.Intent;

/**
 * 対戦の管理をするクラス
 * 対戦時のCPUとOnlineの切り分けを行う
 * 対戦シーンの切替えを行う。
 * 対戦シーン切替えに必要な必須条件のチェックなどを行う。
 *
 */
public class BattleManager {
	
	// コンテキスト
	private BattleActivity activity = null;
	
	private String battleId = null;
	
	private String enemyUserId = null;
	
	private String enemyUserName = null;
	
	
	// 対戦要求クエリ-
	private OnlineQueryBattleReqest battleRequestQery = null;
	
	// 対戦時使用カード登録クエリ-
	private OnlineQueryRegistUsingCard registUsingCardQery = null;
	
	// 対戦相手使用カード取得クエリ-
	private OnlineQueryEnemyUsingCard getEnemyUsingCardQery = null;

	// カード選択シーンの最後に呼び出すメソッドを呼び出す
	public BattleManager(BattleActivity activity) {
		this.activity = activity;
	}
	
	
	/**
	 * 対戦開始時の初期処理
	 */
	public void initBattleAct() {
		
		// 自分の対戦時使用情報を生成
		this.activity.myPlayer = new MyPlayer(this.activity);
		this.activity.myPlayer.createCardBattlerInfo(null,null);
		
		Intent intent = this.activity.getIntent();
		if ("online".equals(intent.getStringExtra("user_mode"))) {
			this.activity.enemyPlayer = new OnlinePlayer(this.activity);
		}
		else {
			// 対戦相手がCPUの場合
			this.activity.enemyPlayer = new CPU01(this.activity);
		}
		
	}
	
	/**
	 * 対戦画面のシーン切替え開始処理
	 */
	public void startBattleScene() {
		
		// オンライン対戦ユーザかどうか判定
		if (this.activity.enemyPlayer instanceof OnlinePlayer) {
			Intent intent = this.activity.getIntent();
			String userMode = intent.getStringExtra("user_mode");
			this.enemyUserId = intent.getStringExtra("user_id");
			this.enemyUserName = intent.getStringExtra("user_name");
			
			String registrationId = intent.getStringExtra("registration_id");
			String battle_id = intent.getStringExtra("battle_id");
			
			if ("0".equals(battle_id)) {
				// 対戦IDが空の場合、まだ、対戦IDが無いので対戦依頼を要求
				// オンラインユーザの場合、対戦相手に対戦要求を発行
				this.battleRequestQery = new OnlineQueryBattleReqest();
				this.battleRequestQery.setUserId(StatusInfo.getUserId(this.activity));
				this.battleRequestQery.setEnemyUserId(this.enemyUserId);
				this.battleRequestQery.setRegistrationId(registrationId);
				new OnlinePoolingTask(this.activity).execute(this.battleRequestQery);
			}
			else {
				// 対戦IDがある場合、相手からの対戦要求を受ける
				this.battleId = battle_id;
				OnlineQueryResponseForBattleReq query = new OnlineQueryResponseForBattleReq();
				query.setBattleId(this.battleId);
				new OnlinePoolingTask(this.activity).execute(query);
			}
			
		}
		else {
			// オンラインユーザでない場合
			
			// 対戦相手（CPU）の情報生成
			this.activity.enemyPlayer.createCardBattlerInfo(null,null);

			// ゲーム開始
			this.activity.getCurrentScene().init();
		}
		
	}

	/**
	 * 対戦要求結果
	 */
	public void successBattleRequest() {
		
		// 対戦IDの取得
		if (this.battleId == null) {
			// 対戦受けた場合は既に対戦IDを保持しているが、対戦要求をしている場合、対戦要求の応答電文に入っている
			// 対戦IDを取得する
			this.battleId = this.battleRequestQery.getResponseData(0, "id");
		}
		
		// 対戦応答待ちループ実行
		OnlineQueryBattleStatus query = new OnlineQueryBattleStatus();
    	query.setBattleId(this.battleId);
    	query.setPoolingCount(60);		// 最大60秒待つ
    	query.setLoopFinishStatus("1"); // 依頼中(0)→開始(1)になるまで、ループさせる。
		new OnlinePoolingTask(this.activity).execute(query);
		
	}

	/**
	 * 対戦準備
	 */
	public void readyBattle() {
		// 応答した場合も、要求して対戦応答ループから戻った場合も本処理で合流する。
		
		// 対戦応答の場合、対戦使用カード情報登録
		this.registUsingCardQery = new OnlineQueryRegistUsingCard();
		this.registUsingCardQery.setBattleId(this.battleId);
		this.registUsingCardQery.setUserId(StatusInfo.getUserId(this.activity));
		this.registUsingCardQery.setCardInfoList(this.activity.myPlayer.cardInfo.getCardInfoList());
		this.registUsingCardQery.setSpecialCardInfoList(this.activity.myPlayer.specialInfo.getCardInfoList());
		this.registUsingCardQery.finishedDataSet();
		new OnlinePoolingTask(this.activity).execute(this.registUsingCardQery);
		
		
		// 対戦応答の場合、対戦相手カード情報取得要求
		this.getEnemyUsingCardQery = new OnlineQueryEnemyUsingCard();
		this.getEnemyUsingCardQery.setBattleId(this.battleId); // 対戦ID
		this.getEnemyUsingCardQery.setUserId(this.enemyUserId); // 対戦相手のユーザID
		new OnlinePoolingTask(this.activity).execute(this.getEnemyUsingCardQery);
		
	}
	
	/**
	 * 対戦相手カード情報取得要求結果
	 */
	public void responseEnemyUsingCard() {
		
		// 対戦相手カード情報が取得できていた場合、相手のカード情報を管理下に登録する。
		this.activity.enemyPlayer.setName(this.enemyUserName);
		this.activity.enemyPlayer.setLifepoint(4000);
		this.activity.enemyPlayer.createCardBattlerInfo(
				this.getEnemyUsingCardQery.getBeetleCards(),
				this.getEnemyUsingCardQery.getSpecialCards());
		
		// ゲーム開始
		this.activity.getCurrentScene().init();
		
	}


	/**
	 * 対戦時選択カード情報取得結果
	 */
	public void responseEnemySelectedCard() {
		
		// OnlinePlayerに選択カードを設定
		
		
		// シーンを変更
		this.activity.changeNextScene();
	}

	
	/**
	 * カード配布シーン終了
	 */
	public void dealCardFinished() {
		this.activity.changeNextScene();
	}

	/**
	 * カード選択シーン終了
	 */
	public void selectCardFinished() {

		// 対戦相手がCPUかオンラインユーザかチェック
		if (this.activity.enemyPlayer instanceof OnlinePlayer) {
			// オンラインユーザの場合、選択したカードを相手先に通知
			
			// 相手先の選択カード取得ポーリングに入る
			
			// ★まだ、選択カードを取得する処理が無いので、
			// 取得したカードを使用してCPUに代行してもらう。
			Player dummy = new CPU01(this.activity);
			dummy.setLifepoint(this.activity.enemyPlayer.getLifepoint());
			dummy.setName(this.activity.enemyPlayer.getName());
			dummy.cardInfo = this.activity.enemyPlayer.cardInfo;
			dummy.specialInfo = this.activity.enemyPlayer.specialInfo;
			this.activity.enemyPlayer = dummy;
			this.activity.changeNextScene();
		}
		else {
			// CPUの場合、次のシーンに移動
			this.activity.changeNextScene();
		}
	}
	
	/**
	 * 対戦アニメーションシーン終了
	 */
	public void animationBattleFinished() {
		this.activity.changeNextScene();
	}
	

	
}
