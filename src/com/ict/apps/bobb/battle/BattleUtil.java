package com.ict.apps.bobb.battle;

import java.util.ArrayList;

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
	public static int[] calcAndViewTotal(ArrayList<BattleCardView> cards) {
		
		int totalPower[] = {0,0};

		// 合計値加算
		for (BattleCardView card : cards) {
			int type = card.getCardInfo().getType();
			if (type == 3) {
				// 攻撃力加算
				totalPower[0] += card.getCardInfo().getAttack();
			}
			else if (type == 4) {
				// 守備力加算
				totalPower[1] += card.getCardInfo().getDefense();
			}
		}
		
		return totalPower;
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
	
	public static int[] judgeAttribute(CardAttribute myAtt, CardAttribute enemyAtt, int...total) {
		
		float winnum  = 1.8f;
		float losenum = 0.7f;
		float nullnum = 1.4f;
		if(myAtt == enemyAtt){
			total[0] = (int)(total[0] * 1.0f);
			total[1] = (int)(total[1] * 1.0f);
		}else if((myAtt == CardAttribute.FIRE)
			   &&(enemyAtt ==CardAttribute.WIND)){
				total[0] = (int)(total[0] * winnum);
				total[1] = (int)(total[1] * winnum);
		}else if((myAtt == CardAttribute.FIRE)
			   &&(enemyAtt ==CardAttribute.WATER)){
				total[0] = (int)(total[0] * losenum);
				total[1] = (int)(total[1] * losenum);
		}else if((myAtt == CardAttribute.WATER)
			   &&(enemyAtt ==CardAttribute.FIRE)){
				total[0] = (int)(total[0] * winnum);
				total[1] = (int)(total[1] * winnum);
		}else if((myAtt == CardAttribute.WATER)
			   &&(enemyAtt ==CardAttribute.WIND)){
				total[0] = (int)(total[0] * losenum);
				total[1] = (int)(total[1] * losenum);
		}else if((myAtt == CardAttribute.WIND)
			   &&(enemyAtt ==CardAttribute.WATER)){
				total[0] = (int)(total[0] * winnum);
				total[1] = (int)(total[1] * winnum);
		}else if((myAtt == CardAttribute.WIND)
			   &&(enemyAtt ==CardAttribute.FIRE)){
				total[0] = (int)(total[0] * losenum);
				total[1] = (int)(total[1] * losenum);
		}else if((myAtt != null)
			   &&(enemyAtt == null)){
				total[0] = (int)(total[0] * nullnum);
				total[1] = (int)(total[1] * nullnum);
		}		
		
		return total;
	}

}