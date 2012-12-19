package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;

import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;

/**
 * CPUの基底クラス
 */
public class CPU extends Player{

	public CPU(Context context) {
		super(context);
	}


	// アイデアリストのルートインスタンス
	private IdeaForSelectCard ideaRoot = null;

	// アイデアリストのルートインスタンス
	private IdeaForSelectSpeCard ideaSpecialRoot = null;

	/**
	 * アイデアを設定する
	 * @param idea
	 */
	public void setIdeaList(IdeaForSelectCard idea) {
		
		if (this.ideaRoot == null) {
			// 基点がNULLなら設定して終了
			this.ideaRoot = idea;
		}
		else {
			
			IdeaForSelectCard ideaList = this.ideaRoot;
			while (true) {
				if(ideaList.getNext() == null ) {
					ideaList.setNext(idea);
					break;
				}
				else {
					ideaList = ideaList.getNext();
				}
			}
		}
	}

	
	/**
	 * 一般カードを選択する
	 * @return  カードを3枚保持する配列、nullの場合選択失敗、適当に上位で設定してくださいますか？(左から順に３まいとか。）
	 */
	public ArrayList<BattleCardView> getSelectCard(Player userInfo, Player enemyInfo) {
		// 判断材料として必要な情報
		// ユーザ情報（LP）、相手のユーザ情報（LP）、手持ちで使用可能な特殊カード、手持ちの一般カード5枚のデータ、CPUの使用済カード（捨てたカード情報）
		// chiceCardの引数で渡す。
		
		// カードを選ぶ
		ArrayList<BattleCardView> selectedCards = this.ideaRoot.choiceCard(userInfo, enemyInfo);
		
		// ここで、カード配列がNULLだったら、適当に選ぶか？
		
		// 使用するカードを設定する。。
		return selectedCards;
	}
	
	/**
	 * 特殊カードを選択する
	 * @return 特殊カード未使用の場合null
	 */
	@Override
	public ArrayList<BattleCardView> getSelectSpacialCard(Player userInfo, Player enemyInfo) {
		// カードを選ぶ
//		ArrayList<BattleCardView> selectedCards = this.ideaSpecialRoot.choiceCard(userInfo, enemyInfo);
		
		// とりあえず、ランダムで１枚取得するロジックにする。
		// あればかならず使用するロジックにする
		
		// 手持ちで未使用の特殊カードを取得する
		ArrayList<BattleCardView> cards = userInfo.specialInfo.getHoldCard();
		
		Random execRandom = new Random();
		int r = execRandom.nextInt(3);
		if (r == 1) {
			// 3回に1回の確率で特殊カードを使用
			if (cards.size() > 0) {
				Random random = new Random();
				
				r = random.nextInt( cards.size() );
				BattleCardView card = cards.get(r);
				userInfo.specialInfo.selectCard(card);
			}
		}
		
		return null;
	}


	@Override
	public void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards) {
		return;
	}

	@Override
	public void createCardBattlerInfo() {
	}
	

}
