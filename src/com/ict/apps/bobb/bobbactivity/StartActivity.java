package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;


public class StartActivity extends BaseActivity {

//一回目のインなのかの判断で使用しているだけですので、後で正しく組み直してください。
	public Boolean count = false;
//ここまで
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        
		this.init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start, menu);
        return true;
    } 
    
    public void startOnClick(View v){
    	
    	Intent intent;
//    	if(this.count == true){
    		intent = new Intent(StartActivity.this, MainMenuActivity.class);
//    	}else{
//    		intent = new Intent(StartActivity.this, UserInfoRegistrationActivity.class);
//    		this.count = true;
//    	}

//		Intent intent = new Intent(StartActivity.this, MainMenuActivity.class);
		startActivity(intent);
    }
    
    public void ruleOnClick(View v){
    	
		Intent intent = new Intent(StartActivity.this, RuleActivity.class);
		startActivity(intent);
		
    }
    
	public void init() {
		
		BeetleKitFactory factory = new BeetleKitFactory(this);

		// DB情報全削除
		factory.deleteAllDbData();


		// 画像テーブル挿入  インストール直後の初回起動時に一回だけ実行する必要がある。
		factory.setImageInfo(1, "カブトムシ", 1, 1, "kabuto01", "ゆるキャラNo1から陥落？？", "無し");
		factory.setImageInfo(2, "アトラスカブト", 2, 1, "imomusi01", "顔デカイでえ！", "無し");
		factory.setImageInfo(3, "ヒラタクワガタ", 3, 1, "kuwagata01", "のりさん大好き♪", "無し");
		factory.setImageInfo(4, "九州産カブトムシ", 1, 2, "kuwagata01", "ぼくドラモン", "無し");
		factory.setImageInfo(5, "アトラスネオ", 2, 2, "beetle2", "ひゅうーひゅうだよ～", "無し");
		factory.setImageInfo(6, "虫A", 3, 2, "beetle3", "ゆるキャラNo6から陥落？？", "無し");
		factory.setImageInfo(7, "クマモン7", 1, 3, "beetle1", "ゆるキャラNo7から陥落？？", "無し");
		factory.setImageInfo(8, "クマモン8", 2, 3, "beetle2", "ゆるキャラNo8から陥落？？", "無し");
		factory.setImageInfo(9, "クマモン9", 3, 3, "beetle3", "ゆるキャラNo9から陥落？？", "無し");
		factory.setImageInfo(1004, "橋下カブト", 1, 3, "beetle1", "教育委員会の糞やろう", "攻撃力アップ");
		
		
		// テストデータ挿入
		// 新規カード取得（バーコード番号からカード生成）  DBにカード情報を登録
//		BreedManager bm = new BreedManager();
		BeetleKit kit = null;
//		kit = bm.generateCardStatusFromBarcode(this, 488889999999l);
//		factory.insertBeetleKitToDB(kit);
//		kit = bm.generateCardStatusFromBarcode(this, 412334549999l);
//		factory.insertBeetleKitToDB(kit);
//		kit = bm.generateCardStatusFromBarcode(this, 488889991290l);
//		factory.insertBeetleKitToDB(kit);
//		kit = bm.generateCardStatusFromBarcode(this, 123412323234l);
//		factory.insertBeetleKitToDB(kit);
//		kit = bm.generateCardStatusFromBarcode(this, 123412321234l);
//		factory.insertBeetleKitToDB(kit);
		
		
		// 虫キットのダミー情報設定
		kit = new BeetleKit();
		kit.setBeetleKitId(1l);						// 虫キットID
		kit.setBarcode_id(199911118876l);			// バーコードID
		kit.setName("カブトムシLv1");					// 名前
		kit.setAttack(800);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto01");			// 画像ファイル名
		kit.setIntroduction("スイカ大好き");			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(this, BattleUseKit.DeckNum.DECK1, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(2l);						// 虫キットID
		kit.setBarcode_id(299911116767l);			// バーコードID
		kit.setName("クワガタLv1");					// 名前
		kit.setAttack(500);							// 攻撃
		kit.setDefense(700);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kuwagata01");			// 画像ファイル名
		kit.setIntroduction("独島はわが領土");			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(this, BattleUseKit.DeckNum.DECK2, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(3l);						// 虫キットID
		kit.setBarcode_id(388711111111l);			// バーコードID
		kit.setName("イモムシ");						// 名前
		kit.setAttack(450);							// 攻撃
		kit.setDefense(1000);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("imomusi01");			// 画像ファイル名
		kit.setIntroduction("アメリカン")	;			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(this, BattleUseKit.DeckNum.DECK3, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(4l);						// 虫キットID
		kit.setBarcode_id(411111118989l);			// バーコードID
		kit.setName("仏産カブトムシD");					// 名前
		kit.setAttack(1150);						// 攻撃
		kit.setDefense(700);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto01");			// 画像ファイル名
		kit.setIntroduction("ジュテーム");				// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(this, BattleUseKit.DeckNum.DECK4, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(5l);						// 虫キットID
		kit.setBarcode_id(566711118889l);			// バーコードID
		kit.setName("露産カブトムシE");					// 名前
		kit.setAttack(700);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("imomusi01");			// 画像ファイル名
		kit.setIntroduction("ミキティ～");				// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(this, BattleUseKit.DeckNum.DECK5, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(6l);						// 虫キットID
		kit.setBarcode_id(598711118888l);			// バーコードID
		kit.setName("ミヤマクワガタ");					// 名前
		kit.setAttack(700);							// 攻撃
		kit.setDefense(1050);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kuwagata01");			// 画像ファイル名
		kit.setIntroduction("はじめましてミヤマです。");		// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(7l);						// 虫キットID
		kit.setBarcode_id(545911119999l);			// バーコードID
		kit.setName("ヘラクレスオオカブト");				// 名前
		kit.setAttack(1200);						// 攻撃
		kit.setDefense(1200);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto01");			// 画像ファイル名
		kit.setIntroduction("世界一でかいで！！");				// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);

		
		// 特殊カードダミー情報設定
		kit = new BeetleKit();
		kit.setBeetleKitId(1001l);					// 虫キットID
		kit.setBarcode_id(111111111112l);			// バーコードID
		kit.setName("いいずカブト");					// 名前
		kit.setEffect("一回やすみ");					// 効果
		kit.setBreedcount(4);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle1");			// 画像ファイル名
		kit.setIntroduction("つよいよ");				// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// 戦闘時使用特殊カードに設定
		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD1, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(1002l);					// 虫キットID
		kit.setBarcode_id(111111111112l);			// バーコードID
		kit.setName("カブトガニ");						// 名前
		kit.setEffect("攻撃力UP");					// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle2");			// 画像ファイル名
		kit.setIntroduction("触るとイタイヨ");			// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// 戦闘時使用特殊カードに設定
		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD2, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(1003l);					// 虫キットID
		kit.setBarcode_id(111111111113l);			// バーコードID
		kit.setName("タモリ");							// 名前
		kit.setEffect("カード入替");					// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle3");		// 画像ファイル名
		kit.setIntroduction("髪切った？とよく聞きます");	// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// 戦闘時使用特殊カードに設定
		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD3, kit);
		
		kit = new BeetleKit();
		kit.setBeetleKitId(1004l);					// 虫キットID
		kit.setBarcode_id(111111111114l);			// バーコードID
		kit.setName("はしした");						// 名前
		kit.setEffect("攻撃力UP");					// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle3");			// 画像ファイル名
		kit.setIntroduction("教育委員会のクソ野郎");		// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);

		
		// ユーザ情報クラスのアクセス例
		StatusInfo.setUserName(this, "Noririn");
		StatusInfo.setLP(this, 2000);
		StatusInfo.setLevel(this, 1);
		StatusInfo.setButtleCount(this, 123);
		StatusInfo.setEXP(this, 999);
				
		Log.d("★★", "Name　：　" + StatusInfo.getUserName(this));
		Log.d("★★", "LP　：　" + StatusInfo.getLP(this));
		Log.d("★★", "Lv　：　" + StatusInfo.getLevel(this));
		Log.d("★★", "count　：　" + StatusInfo.getButtleCount(this));
		Log.d("★★", "EXP　：　" + StatusInfo.getEXP(this));


		// 戦闘時使用キットクラスの使用例
		BeetleKit beetlekit1 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK1);
		BeetleKit beetlekit2 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK2);
		BeetleKit beetlekit3 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK3);
		BeetleKit beetlekit4 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK4);
		BeetleKit beetlekit5 = BattleUseKit.getUseKit(this, BattleUseKit.DeckNum.DECK5);

		Log.d("★★★★★★★★", "-----------------------------------");
		Log.d("★", "ID　：　" + beetlekit1.getBeetleKitId());
		Log.d("★", "名前　： " + beetlekit1.getName());
		Log.d("★", "攻撃　： " + beetlekit1.getAttack());
		Log.d("★", "防御　： " + beetlekit1.getDefense());
		Log.d("★★★★★★★★", "-----------------------------------");

		// 取得した虫キットからカード生成
		Card[] cards = beetlekit1.createBeetleCards();
		for (int j = 0; j < cards.length; j++) {
			Log.d("★", "その" + j );
			Log.d("★", "ID　：　" + cards[j].getBeetleKitId());
			Log.d("★", "名前　： " + cards[j].getName());
			Log.d("★", "種別　： " + cards[j].getType());
			Log.d("★", "属性　： " + ((BeetleCard)cards[j]).getAttribute());
			Log.d("★", "攻撃　： " + ((BeetleCard)cards[j]).getAttack());
			Log.d("★", "防御　： " + ((BeetleCard)cards[j]).getDefense());
			Log.d("★", "FILE　： " + cards[j].getImageFileName());
			Log.d("★", "説明　： " + cards[j].getIntroduction());
			Log.d("★", "-----------------------------------");
		}

		// 戦闘時使用特殊カードの使用例
		BeetleKit specialkit1 = BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD1);
		BeetleKit specialkit2 = BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD2);
		BeetleKit specialkit3 = BattleUseSpecialCard.getUseKit(this, BattleUseSpecialCard.CardNum.CARD3);

		Card[] specards = specialkit1.createBeetleCards();
		for (int j = 0; j < specards.length; j++) {
			Log.d("★", "その" + j );
			Log.d("★", "ID　：　" + specards[j].getBeetleKitId());
			Log.d("★", "名前　： " + specards[j].getName());
			Log.d("★", "種別　： " + specards[j].getType());
			Log.d("★", "FILE　： " + specards[j].getImageFileName());
			Log.d("★", "効果　： " + ((SpecialCard)specards[j]).getEffect());
			Log.d("★", "説明　： " + specards[j].getIntroduction());
			Log.d("★", "-----------------------------------");
		}


	}

}
