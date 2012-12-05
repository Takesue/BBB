package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;

import android.content.Context;


import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;

/**
 * オンラインでの対戦相手クラス
 * バックグラウンドでPoolingして、対戦相手の情報を採取します。
 */
public class OnlinePlayer extends Player{

	/**
	 * コンストラクタ
	 * @param context
	 */
	public OnlinePlayer(Context context) {
		super(context);
	}


	/**
	 * 一般カードを選択する
	 * @return  カードを3枚保持する配列、nullの場合選択失敗、適当に上位で設定してくださいますか？(左から順に３まいとか。）
	 *          
	 */
	public ArrayList<BattleCardView> getSelectCard(Player userInfo, Player enemyInfo) {
		// 対戦相手のカード情報を取得するタスクを呼び出す
		
		return new ArrayList<BattleCardView>();
	}
	
	/**
	 * 特殊カードを選択する
	 * @return 特殊カード未使用の場合null
	 */
	@Override
	public ArrayList<BattleCardView> getSelectSpacialCard(Player userInfo, Player enemyInfo) {
		// カードを選ぶ
		
		// 使用するカードを設定する。。
		
		return new ArrayList<BattleCardView>();
	}

	
	@Override
	public void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards) {
		
		this.setCardInfoToCardBattlerInfo(cards);
		this.setCardInfoToCardSpecialInfo(specialCards);
		return;
	}
	
	
	
	

}
