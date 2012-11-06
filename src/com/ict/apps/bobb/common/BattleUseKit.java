package com.ict.apps.bobb.common;

import com.ict.apps.bobb.data.BeetleKit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 対戦時使用の虫キット情報クラス
 * @author take
 *
 */
public class BattleUseKit {

	// SharedPref名
	private static final String NAME_BATTLE_USE_KIT = "BattleDeck";
	// 対戦デッキ１
	private static final String DECK1 = "deck1";
	// 対戦デッキ２
	private static final String DECK2 = "deck2";
	// 対戦デッキ３
	private static final String DECK3 = "deck3";
	// 対戦デッキ４
	private static final String DECK4 = "deck4";
	// 対戦デッキ５
	private static final String DECK5 = "deck5";
	
	public enum DeckNum {
		DECK1,
		DECK2,
		DECK3,
		DECK4,
		DECK5,
	}
	
	/**
	 * SharedPreferencesへアクセス
	 * @param context
	 * @return
	 */
	private static SharedPreferences openPreferences(Context context) {
		return context.getSharedPreferences(BattleUseKit.NAME_BATTLE_USE_KIT, Context.MODE_PRIVATE);
	}

	/**
	 * 対戦時使用の虫キットを取得する。
	 * 
	 * BattleUseKit.DeckNum.DECK1
	 * 
	 * @param context
	 * @param deck　格納番号DECK1~DECK5 　　例）　 BattleUseKit.DeckNum.DECK1
	 * @return　虫キットインスタンス
	 */
	public static BeetleKit getUseKit(Context context, DeckNum deck) {
		
		String deckNum = getDeckNum(deck);

		SharedPreferences pref = BattleUseKit.openPreferences(context);
		long beetleId = pref.getLong(deckNum, 0);
		
		if (beetleId == 0) {
			// 無い場合は、デフォルトのカブト虫キットを設定する。
			beetleId = 1;
			pref = BattleUseKit.openPreferences(context);
			SharedPreferences.Editor editor = pref.edit();
			editor.putLong(deckNum, beetleId);
			editor.commit();
		}
		
		// 虫キットIDをキーに虫キットインスタンスを生成する。
		BeetleKitFactory factory = new BeetleKitFactory(context);
		BeetleKit beelteKit = factory.getBeetleKit(beetleId);

		return beelteKit;
		
	}

	/**
	 * 対戦時使用の虫キットを設定する。
	 * デッキ番号を指定する必要があります。
	 * @param context
	 * @param deck デッキ番号
	 * @param kit
	 */
	public static void setUseKit(Context context, DeckNum deck, BeetleKit kit) {
		
		String deckNum = getDeckNum(deck);
		
		// カブト虫キットを設定する。
		SharedPreferences pref = BattleUseKit.openPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(deckNum, kit.getBeetleKitId());
		editor.commit();
		
	}

	/**
	 * 列挙型のデッキ番号を検索キーに変換する
	 * @param deck 列挙型　デッキ番号
	 * @return
	 */
	private static String getDeckNum(DeckNum deck) {
		String deckNum = null;
		if (DeckNum.DECK1 == deck) {
			deckNum = BattleUseKit.DECK1;
		}
		else if (DeckNum.DECK2 == deck) {
			deckNum = BattleUseKit.DECK2;
		}
		else if (DeckNum.DECK3 == deck) {
			deckNum = BattleUseKit.DECK3;
		}
		else if (DeckNum.DECK4 == deck) {
			deckNum = BattleUseKit.DECK4;
		}
		else if (DeckNum.DECK5 == deck) {
			deckNum = BattleUseKit.DECK5;
		}
		return deckNum;
	}
	
	/**
	 * 対戦デッキに設定されている全ての虫キットのIDを取得する
	 * @return
	 */
	public static long[] getAllSettingDeckID(Context context) {
		
		String[] deckList = {
				BattleUseKit.DECK1,
				BattleUseKit.DECK2,
				BattleUseKit.DECK3,
				BattleUseKit.DECK4,
				BattleUseKit.DECK5
		};
		
		long[] beetleIdList = new long[deckList.length];
		
		SharedPreferences pref = BattleUseKit.openPreferences(context);
		
		for (int i = 0; i < deckList.length; i++) {
			beetleIdList[i] = pref.getLong(deckList[i], 0);
		}
		
		return beetleIdList;
	}

	
}
