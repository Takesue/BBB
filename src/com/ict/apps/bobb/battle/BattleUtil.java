package com.ict.apps.bobb.battle;

import java.util.ArrayList;

import com.ict.apps.bobb.battle.player.Player;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.data.CardAttribute;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BattleUtil extends Activity {

	/**
	 * 攻撃力と守備力の合計値を算出して表示する
	 */
	public static void calcAndViewTotal(Player player) {
		
		ArrayList<BattleCardView> cards = player.cardInfo.getSelectedCard();
		// 合計値加算
		for (BattleCardView card : cards) {
			int type = card.getCardInfo().getType();
			if (type == 3) {
				// 攻撃力加算
				player.totalAttack += card.getCardInfo().getAttack();
			}
			else if (type == 4) {
				// 守備力加算
				player.totalDefense += card.getCardInfo().getDefense();
			}
		}
		
		return;
	}
	
	/**
	 * 選択された三枚のカードでの属性を取得
	 * @return
	 */
	public static CardAttribute getAttribute(ArrayList<BattleCardView> cards){
		CardAttribute att[] = new CardAttribute[3];
		CardAttribute attret = null;
		ArrayList<BattleCardView> CardList = cards;
		for(int i = 0; i < 3; i++){
			att[i] = CardList.get(i).getCardInfo().getAttribute();
		}
		if((att[0] == att[1])
		 &&(att[0] == att[2])){
			attret = att[0];
		}
		
		return attret;
	}
	
	/**
	 * 属性合わせでの値を取得
	 * @return
	 */
	public static void judgeAttribute(Player myPlayer, Player enemyPlayer) {
		
//		float winnum  = 1.8f;
//		float losenum = 0.7f;
//		float nullnum = 1.4f;
		
		CardAttribute myAtt = BattleUtil.getAttribute(myPlayer.cardInfo.getSelectedCard()); 
		CardAttribute enemyAtt = BattleUtil.getAttribute(enemyPlayer.cardInfo.getSelectedCard());
		
		// 勝ち負け判定による倍率を取得する
		float rate = getJudgeResult(myAtt, enemyAtt);
		
		myPlayer.totalAttack = (int)(myPlayer.totalAttack * rate);
		myPlayer.totalDefense = (int)(myPlayer.totalDefense * rate);

//		
//		if(myAtt == enemyAtt){
//			total[0] = (int)(total[0] * 1.0f);
//			total[1] = (int)(total[1] * 1.0f);
//		}else if((myAtt == CardAttribute.FIRE)
//			   &&(enemyAtt ==CardAttribute.WIND)){
//				total[0] = (int)(total[0] * winnum);
//				total[1] = (int)(total[1] * winnum);
//		}else if((myAtt == CardAttribute.FIRE)
//			   &&(enemyAtt ==CardAttribute.WATER)){
//				total[0] = (int)(total[0] * losenum);
//				total[1] = (int)(total[1] * losenum);
//		}else if((myAtt == CardAttribute.WATER)
//			   &&(enemyAtt ==CardAttribute.FIRE)){
//				total[0] = (int)(total[0] * winnum);
//				total[1] = (int)(total[1] * winnum);
//		}else if((myAtt == CardAttribute.WATER)
//			   &&(enemyAtt ==CardAttribute.WIND)){
//				total[0] = (int)(total[0] * losenum);
//				total[1] = (int)(total[1] * losenum);
//		}else if((myAtt == CardAttribute.WIND)
//			   &&(enemyAtt ==CardAttribute.WATER)){
//				total[0] = (int)(total[0] * winnum);
//				total[1] = (int)(total[1] * winnum);
//		}else if((myAtt == CardAttribute.WIND)
//			   &&(enemyAtt ==CardAttribute.FIRE)){
//				total[0] = (int)(total[0] * losenum);
//				total[1] = (int)(total[1] * losenum);
//		}else if((myAtt != null)
//			   &&(enemyAtt == null)){
//				total[0] = (int)(total[0] * nullnum);
//				total[1] = (int)(total[1] * nullnum);
//		}

		return;
	}
	
	/**
	 * 属性じゃんけんの判定結果、倍率を返却する。
	 * @param myAtt
	 * @return
	 */
	private static float getJudgeResult(CardAttribute myAtt, CardAttribute enemyAtt) {
		
		float winnum  = 1.8f;
		float losenum = 0.7f;
		float nullnum = 1.4f;
		
		float retValue = nullnum;
		
		// 判定MAP配列　配列0が自分の属性の場合、相手が配列0ならアイコ、配列1なら自分の勝ち、配列2なら相手の勝ち
		CardAttribute[][] judgeMap = {
				{CardAttribute.FIRE, CardAttribute.WIND, CardAttribute.WATER},
				{CardAttribute.WATER, CardAttribute.FIRE, CardAttribute.WIND},
				{CardAttribute.WIND, CardAttribute.WATER, CardAttribute.FIRE}
		};
		
		// 属性がどちらもNULLでない場合、MAPに従って勝ち負け判定を行う
		if ((myAtt != null) && (enemyAtt != null)){
			for (int i= 0; i < judgeMap.length; i++) {
				if (myAtt == judgeMap[i][0]) {
					if (enemyAtt == judgeMap[i][0]) {
						// アイコ
						retValue = 1.0f;
						break;
					} else if (enemyAtt == judgeMap[i][1]) {
						// Myが勝ち
						retValue = winnum;
						break;
					} else if (enemyAtt == judgeMap[i][1]) {
						// enemyが勝ち
						retValue = losenum;
						break;
					}
				}
			}
		}
		else if (myAtt == null){
			retValue = 1.0f;
		}
		
		return retValue;
	}

}