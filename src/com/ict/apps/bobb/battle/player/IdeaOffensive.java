package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import com.ict.apps.bobb.bobbactivity.BattleCardView;

/**
 * ５枚から攻撃を２枚選ぶ
 * 選択基準は合計値高
 */
public class IdeaOffensive extends IdeaForSelectCard {

	@Override
	protected ArrayList<BattleCardView> judge(Player userInfo, Player enemyInfo) {
		ArrayList<BattleCardView> cardList = null;
		
		// 攻撃が２枚あるかどうか調べる
		ArrayList<BattleCardView> cards = enemyInfo.cardInfo.getHoldCard();
		int attackCnt = 0;
		int defenseCnt = 0;
		for(BattleCardView card : cards) {
			if(card.getCardInfo().getType() == 3) {
				attackCnt++;
			}
			else if(card.getCardInfo().getType() == 4) {
				defenseCnt++;
			}
		}
		if ((attackCnt < 2) || (defenseCnt < 1)){
			// 無い場合、nullで返却
			return null;
		}
		
		
		// 守備が１枚以上あるかどうか調べる
		// 無い場合、nullで返却
		
		// 攻撃カードが2枚以上ある場合、攻撃カードのうち数値が高いものを2枚選ぶ
		ArrayList<BattleCardView> attackCards = this.getAttackCards(2, cards);
		if (attackCards != null) {
			cardList = new ArrayList<BattleCardView>();
			cardList.addAll(attackCards);
		}
		
		// NULLでない場合、処理継続
		if (cardList != null) {
			// 守備力で降順ソート
			// 先頭から1枚を採取
			ArrayList<BattleCardView> defenseCards = this.getDefenseCards(1, cards);
			if (defenseCards != null) {
				cardList.addAll(defenseCards);
			}
			else {
				// 守備カードが取得できない場合NULL
				cardList = null;
			}
		}
		
		return cardList;
	}
	
	
	/**
	 * 攻撃力が高い順から指定した数分のカードのArrayListを取得する。
	 */
	private ArrayList<BattleCardView> getAttackCards(int num, ArrayList<BattleCardView> cards) {
		
		ArrayList<BattleCardView> cardList = null;

		// ArrayListの数より指定した数が多い場合,または、配列がNULLの場合エラーと判断してNULLを返却
		if ((num <= cards.size()) && (cards != null)) {
			
			// 攻撃力で降順ソート
			cardList = new ArrayList<BattleCardView>();
			for(int i = 0; i < num; i++) {
				// 指定数分追加する
				cardList.add(cardList.get(i));
			}
		}
		
		return cardList;
	}
	

	/**
	 * 守備力が高い順から指定した数分のカードのArrayListを取得する。
	 */
	private ArrayList<BattleCardView> getDefenseCards(int num, ArrayList<BattleCardView> cards) {
		
		ArrayList<BattleCardView> cardList = null;

		// ArrayListの数より指定した数が多い場合,または、配列がNULLの場合エラーと判断してNULLを返却
		if ((num <= cards.size()) && (cards != null)) {
			
			// 守備力で降順ソート
			Collections.sort(cardList, new Comparator<BattleCardView>(){
				public int compare(BattleCardView t1, BattleCardView t2) {
					return t2.getCardInfo().getAttack() - t1.getCardInfo().getAttack();
					}
				});
			
			cardList = new ArrayList<BattleCardView>();
			for(int i = 0; i < num; i++) {
				// 指定数分追加する
				cardList.add(cardList.get(i));
			}
		}
		
		return cardList;
	}

}
