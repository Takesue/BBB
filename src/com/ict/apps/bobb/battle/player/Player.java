package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.ict.apps.bobb.battle.CardInfo;
import com.ict.apps.bobb.battle.SpecialCardInfo;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.SpecialCard;

/**
 * 対戦相手のインターフェース
 */
public abstract class Player {
	
	// ユーザ名
	private String name = null;
	
	// LP
	private int lifepoint = 0;

	// LP
	private int lpMax = 0;

	// Level
	private int Level = 0;

	// 対戦依頼
	private String request = "0";

	// ユーザの対戦時特殊カードを一元保持
	public CardInfo cardInfo = null;

	// ユーザの対戦時特殊カードを一元保持
	public SpecialCardInfo specialInfo = null;
	
	// 合計攻撃力
	public int totalAttack = 0;
	
	// 合計守備力
	public int totalDefense = 0;
	
	// コンテキストを保持
	protected Context context = null;
	
	// アイコンのリソースID
	private int icResourceId = 0;
	
	/**
	 * コンストラクタ
	 * @param context
	 */
	public Player(Context context) {
		this.context = context;
	}

	/**
	 * 名前を取得する
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名前を設定する
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ライフポイントを取得する
	 * @return
	 */
	public int getLifepoint() {
		return lifepoint;
	}

	/**
	 * ライフポイントを設定する
	 * @param lifepoint
	 */
	public void setLifepoint(int lifepoint) {
		this.lifepoint = lifepoint;
	}

	/**
	 * ライフポイントMAXを取得する
	 * @return
	 */
	public int getLpMax() {
		return lpMax;
	}

	/**
	 * ライフポイントMAXを設定する
	 * @return
	 */
	public void setLpMax(int lpMax) {
		this.lpMax = lpMax;
	}

	/**
	 * レベル取得
	 * @return
	 */
	public int getLevel() {
		return Level;
	}

	/**
	 * レベル設定
	 * @param level
	 */
	public void setLevel(int level) {
		Level = level;
	}

	/**
	 * 対戦要求有無取得　０：なし　１：あり
	 * @return
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * 対戦要求有無設定　０：なし　１：あり
	 * @param request
	 */
	public void setRequest(String request) {
		this.request = request;
	}
	
	/**
	 * リソースID設定
	 * @return
	 */
	public int getIcResourceId() {
		return icResourceId;
	}

	/**
	 * リソースID取得
	 * @param icResourceId
	 */
	public void setIcResourceId(int icResourceId) {
		this.icResourceId = icResourceId;
	}

	
	/**
	 * 自分自身で使用するカード対戦時に必要な情報一式を生成する
	 * @return
	 */
	public abstract void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards);
	
	/**
	 * 自分自身で使用するカード対戦時に必要な情報一式を自前で生成する
	 * @return
	 */
	public abstract void createCardBattlerInfo();

	/**
	 * 一般カードを選択する
	 * @return  カードを3枚保持する配列、nullの場合選択失敗
	 */
	public abstract ArrayList<BattleCardView> getSelectCard(Player userInfo, Player enemyInfo);
	
	/**
	 * 特殊カードを選択する
	 * @return 特殊カード未使用の場合null
	 */
	public abstract ArrayList<BattleCardView> getSelectSpacialCard(Player userInfo, Player enemyInfo);

	/**
	 * 対戦デッキに設定されている虫キット情報を元に、６枚のカードを、管理テーブルに設定する
	 * @param beetlekit
	 */
	protected void setCardInfoToCardBattlerInfo(BeetleCard[] cards) {
		
		if (this.cardInfo == null) {
			this.cardInfo = new CardInfo();
		}
		
		for (int i = 0; i < cards.length; i++) {
			
//			Integer ii = cards[i].getType();
//			Log.d("cardslength", ii.toString());
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			
			viewCard.setControlActivity((BattleActivity)this.context);
			
			// 自分の札だけクリックが利くようにする。
			if ((((BattleActivity)this.context).myPlayer != null) 
				&& (((BattleActivity)this.context).myPlayer.equals(this))) {
				// カードを長押しした場合のイベントリスナ
				viewCard.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {

						// ボタン長押
						((BattleActivity)context).onLongClickCard((BattleCardView)v);
						return true;
					}
				});
			}
			
			viewCard.setBeetleCard(cards[i]);
			
			Log.d("setCardInfoToCardBattlerInfo", "=============================");
			Log.d("setCardInfoToCardBattlerInfo", "cardNum:" + cards[i].getCardNum());
			Log.d("setCardInfoToCardBattlerInfo", "Name:" + cards[i].getName());
			Log.d("setCardInfoToCardBattlerInfo", "type:" + cards[i].getType());
			Log.d("setCardInfoToCardBattlerInfo", "attack:" + cards[i].getAttack());
			Log.d("setCardInfoToCardBattlerInfo", "defense:" + cards[i].getDefense());
			Log.d("setCardInfoToCardBattlerInfo", "Attribute:" + cards[i].getAttribute());
			
			
			this.cardInfo.setBattleCards(viewCard);
			
		}
	}
	
	/**
	 * 対戦デッキに設定されている特殊虫キット情報を元にカードを、管理テーブルに設定する
	 * @param specialkit
	 */
	protected void setCardInfoToCardSpecialInfo(SpecialCard[] cards) {
		
		if (this.specialInfo == null) {
			this.specialInfo = new SpecialCardInfo();
		}

		for (int i = 0; i < cards.length; i++) {
			
			Integer ii = cards[i].getType();
			Log.d("cardslength", ii.toString());
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards, null);
			
			viewCard.setControlActivity((BattleActivity)this.context);
			
			
			viewCard.setSpecialCard(cards[i]);
			
			this.specialInfo.setSpecialCards(viewCard);
			
		}
	}
	
	/**
	 * 攻撃力、守備力合算値クリア
	 */
	public void totalValueClear() {
		this.totalAttack = 0;
		this.totalDefense = 0;
	}



}
