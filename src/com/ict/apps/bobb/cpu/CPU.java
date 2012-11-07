package com.ict.apps.bobb.cpu;

import com.ict.apps.bobb.data.Card;

/**
 * CPUの基底クラス
 */
public class CPU {

	// アイデアリストのルートインスタンス
	private IdeaForSelectCard ideaRoot = null;

	// アイデアリストのルートインスタンス
	private IdeaForSelectSpeCard ideaSpecialRoot = null;

	/**
	 * アイデアを設定する
	 * @param idea
	 */
	public void setIdeaList(IdeaForSelectCard idea) {
		
		IdeaForSelectCard ideaList = this.ideaRoot;
		while (true) {
			if(ideaList == null ) {
				ideaList = idea;
				break;
			}
			else {
				ideaList = ideaList.getNext();
			}
		}
		this.ideaRoot = ideaList;
	}

	
	/**
	 * アイデアを設定する
	 * @param idea
	 */
	public void setIdeaSpacialList(IdeaForSelectSpeCard idea) {
		
		IdeaForSelectSpeCard ideaList = this.ideaSpecialRoot;
		while (true) {
			if(ideaList == null ) {
				ideaList = idea;
				break;
			}
			else {
				ideaList = ideaList.getNext();
			}
		}
		this.ideaSpecialRoot = ideaList;
	}

	/**
	 * 一般カードを選択する
	 * @return  カードを3枚保持する配列、nullの場合選択失敗、適当に上位で設定してくださいますか？(左から順に３まいとか。）
	 */
	public Card[] getSelectCard(Object info) {
		// 判断材料として必要な情報
		// ユーザ情報（LP）、相手のユーザ情報（LP）、手持ちで使用可能な特殊カード、手持ちの一般カード5枚のデータ、CPUの使用済カード（捨てたカード情報）
		// chiceCardの引数で渡す。
		
		// カードを選ぶ
		Card[] selectedCards = this.ideaRoot.choiceCard(info);
		
		// ここで、カード配列がNULLだったら、適当に選ぶか？
		
		// 使用するカードを設定する。。
		return selectedCards;
	}
	
	/**
	 * 特殊カードを選択する
	 * @return 特殊カード未使用の場合null
	 */
	public Card[] getSelectSpacialCard(Object info) {
		// カードを選ぶ
		Card[] selectedCards = this.ideaSpecialRoot.choiceCard(info);
		
		// 使用するカードを設定する。。
		
		return null;
	}
	

}
