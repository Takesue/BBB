package com.ict.apps.bobb.battle.player;

import java.util.ArrayList;
import java.util.Arrays;

import com.ict.apps.bobb.battle.CardInfo;
import com.ict.apps.bobb.battle.SpecialCardInfo;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 初期対戦用CPU
 */
public class CPU01 extends CPU {
	
	/**
	 * コンストラクタ
	 */
	public CPU01(Context context) {
		super(context);
		
		this.setName("CPU01");
		this.setLifepoint(Integer.parseInt((String)context.getText(R.string.maxLp)));
		this.setLpMax(this.getLifepoint());
		this.setLevel(1);
		this.setIcResourceId(R.drawable.cpu01_ic);
		
		// CPU思考回路の設定
		// 判断の優先度順に設定する。
		this.setIdeaList(new IdeaFirstInFirstout());
		
	}

	@Override
	public void createCardBattlerInfo(BeetleCard[] cards, SpecialCard[] specialCards) {
		
		cardInfo = new CardInfo();
		specialInfo = new SpecialCardInfo();
		
		// CPUの使用する使用する虫キットを取得する
		// カードを管理テーブルに設定する。
		this.setCardInfoToCardBattlerInfo(cards);
		this.setCardInfoToCardSpecialInfo(specialCards);
		
		return;
	}
	
	/**
	 * 対戦時に使用するカードを生成する
	 */
	@Override
	public void createCardBattlerInfo() {
		
		// 生成カードの一時保持用インスタンス
		ArrayList<Card> cardList = new ArrayList<Card>();
		ArrayList<Card> specialCardList = new ArrayList<Card>();

		/*-----------------------------------
		 * 
		 * 使用する一般カードの生成
		 * 
		 ------------------------------------*/
		BeetleKit kit;
		kit = new BeetleKit();
		
		// No1
		kit.setBeetleKitId(900l);					// 虫キットID
		kit.setBarcode_id(199911118876l);			// バーコードID
		kit.setName("オークカブト");						// 名前
		kit.setAttack(900);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(900);						// 画像ID
		kit.setImageFileName("oakabuto01");			// 画像ファイル名
		kit.setIntroduction("フンガ～♪");			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		cardList.addAll(Arrays.asList(kit.createBeetleCards()));

		// No2
		kit = new BeetleKit();
		kit.setBeetleKitId(900l);					// 虫キットID
		kit.setBarcode_id(199911118876l);			// バーコードID
		kit.setName("オークカブト");						// 名前
		kit.setAttack(900);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(900);						// 画像ID
		kit.setImageFileName("oakabuto01");			// 画像ファイル名
		kit.setIntroduction("フンガ～♪");			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		cardList.addAll(Arrays.asList(kit.createBeetleCards()));

		// No3
		kit = new BeetleKit();
		kit.setBeetleKitId(900l);					// 虫キットID
		kit.setBarcode_id(199911118876l);			// バーコードID
		kit.setName("オークカブト");						// 名前
		kit.setAttack(900);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(900);						// 画像ID
		kit.setImageFileName("oakabuto01");			// 画像ファイル名
		kit.setIntroduction("フンガ～♪");			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		cardList.addAll(Arrays.asList(kit.createBeetleCards()));

		// No4
		kit = new BeetleKit();
		kit.setBeetleKitId(900l);					// 虫キットID
		kit.setBarcode_id(199911118876l);			// バーコードID
		kit.setName("オークカブト");						// 名前
		kit.setAttack(900);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(900);						// 画像ID
		kit.setImageFileName("oakabuto01");			// 画像ファイル名
		kit.setIntroduction("フンガ～♪");				// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		cardList.addAll(Arrays.asList(kit.createBeetleCards()));

		// No5
		kit = new BeetleKit();
		kit.setBeetleKitId(900l);					// 虫キットID
		kit.setBarcode_id(199911118876l);			// バーコードID
		kit.setName("オークカブト");						// 名前
		kit.setAttack(900);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(900);						// 画像ID
		kit.setImageFileName("oakabuto01");			// 画像ファイル名
		kit.setIntroduction("フンガ～♪");			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		cardList.addAll(Arrays.asList(kit.createBeetleCards()));

		/*-----------------------------------
		 * 
		 * 使用する特殊カードの生成
		 * 
		 ------------------------------------*/
		kit = new BeetleKit();
		kit.setBeetleKitId(1001l);					// 虫キットID
		kit.setBarcode_id(111111111112l);			// バーコードID
		kit.setName("いいずカブト");					// 名前
		kit.setEffect("攻撃力２倍");					// 効果
		kit.setBreedcount(4);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto01");			// 画像ファイル名
		kit.setIntroduction("トガッテルぜ～");				// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(2);							// 特殊効果ID
		specialCardList.addAll(Arrays.asList(kit.createBeetleCards()));
		
		kit = new BeetleKit();
		kit.setBeetleKitId(1002l);					// 虫キットID
		kit.setBarcode_id(111111111112l);			// バーコードID
		kit.setName("カブトガニ");						// 名前
		kit.setEffect("守備力２倍");					// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto01");			// 画像ファイル名
		kit.setIntroduction("触るとイタイヨ");			// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(3);							// 特殊効果ID
		specialCardList.addAll(Arrays.asList(kit.createBeetleCards()));

		kit = new BeetleKit();
		kit.setBeetleKitId(1003l);					// 虫キットID
		kit.setBarcode_id(111111111113l);			// バーコードID
		kit.setName("超蝶々");							// 名前
		kit.setEffect("相手攻撃力１／２");					// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto01");			// 画像ファイル名
		kit.setIntroduction("美しすぎる・・・");			// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(4);							// 特殊効果ID
		specialCardList.addAll(Arrays.asList(kit.createBeetleCards()));
		
		BeetleCard[] cards = new BeetleCard[cardList.size()];
		cards = cardList.toArray(cards);

		SpecialCard[] specialCards = new SpecialCard[specialCardList.size()];
		specialCards = specialCardList.toArray(specialCards);

		this.createCardBattlerInfo(cards, specialCards);
	}


}
