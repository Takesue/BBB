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

	// ユーザの対戦時特殊カードを一元保持
	public CardInfo cardInfo = null;

	// ユーザの対戦時特殊カードを一元保持
	public SpecialCardInfo specialInfo = null;

	// コンテキストを保持
	protected Context context = null;

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
	 * 自分自身で使用するカード対戦時に必要な情報一式を生成する
	 * @return
	 */
	public abstract void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards);
	
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
			
			Integer ii = cards[i].getType();
			Log.d("cardslength", ii.toString());
			
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


}
