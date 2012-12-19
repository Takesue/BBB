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
import com.ict.apps.bobb.online.OnlineQuery;
import com.ict.apps.bobb.online.OnlineResponseListener;
import com.ict.apps.bobb.online.OnlineUtil;
import com.ict.apps.bobb.online.OnlineOneTimeTask;
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQueryUserRegister;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfoRegistrationActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_userinforegistration);

		// ブロードキャストレシーバの登録
		this.registerReceiver(this.mHandleQeryCompReceiver, new IntentFilter(OnlineUtil.QERY_COMPLETE_ACTION));

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// ブロードキャストレシーバの解除
		this.unregisterReceiver(mHandleQeryCompReceiver);

	}



	public void registOnClick(View v) {
		this.registerUserInfo();
		createDefauleCards(this);

	}
	
	public void finishActivity() {
		this.setResult(RESULT_OK, new Intent());
		this.finish();
	}
	
	
	/**
	 * ユーザ情報を登録する
	 */
	private void registerUserInfo() {

		// 入力されたユーザ名のチェック
		final String name = ((EditText)this.findViewById(R.id.registUserName)).getText().toString();
		if ((name == null) || ("".equals(name))){
			Toast.makeText(this, "入力された名前が不正です。再入力してください。", Toast.LENGTH_LONG).show();
			return ;
		}
		
		
		StatusInfo.setUserName(this, name);
		OnlineQueryUserRegister query = new OnlineQueryUserRegister();
		query.setUserName(StatusInfo.getUserName(this));
		query.setListner(new OnlineResponseListener() {
			@Override
			public void response(Context context, OnlineQuery query, Integer result) {
				// ユーザIDを端末に保存
				StatusInfo.setUserId(context, query.getResponseData(0, "id"));
				Log.d("★★JsonData", query.getResponseData(0, "user_name"));	
			}
		});
		new OnlinePoolingTask(this).execute(query);

	}
	
	// ブロードキャストメッセージ
	// バックグラウンドのPoolingが終了したことを受ける
	private final BroadcastReceiver mHandleQeryCompReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 画面終了
			finishActivity();
		}
	};
	
	public static void createDefauleCards(Context context) {
		
		BeetleKitFactory factory = new BeetleKitFactory(context);

		// DB情報全削除
		factory.deleteAllDbData();


		// 画像テーブル挿入  インストール直後の初回起動時に一回だけ実行する必要がある。
		factory.setImageInfo(1, "カブトムシ", 1, 1, "kabuto01", "ゆるキャラNo1から陥落？？", "無し",0);
		factory.setImageInfo(2, "アトラスカブト", 2, 1, "imomusi01", "顔デカイでえ！", "無し",0);
		factory.setImageInfo(3, "ヒラタクワガタ", 3, 1, "kuwagata01", "のりさん大好き♪", "無し",0);
		factory.setImageInfo(4, "九州産カブトムシ", 1, 2, "kuwagata01", "ぼくドラモン", "無し",0);
		factory.setImageInfo(5, "アトラスネオ", 2, 2, "beetle2", "ひゅうーひゅうだよ～", "無し",0);
		factory.setImageInfo(6, "虫A", 3, 2, "beetle3", "ゆるキャラNo6から陥落？？", "無し",0);
		factory.setImageInfo(7, "クマモン7", 1, 3, "beetle1", "ゆるキャラNo7から陥落？？", "無し",0);
		factory.setImageInfo(8, "クマモン8", 2, 3, "beetle2", "ゆるキャラNo8から陥落？？", "無し",0);
		factory.setImageInfo(9, "クマモン9", 3, 3, "beetle3", "ゆるキャラNo9から陥落？？", "無し",0);
		factory.setImageInfo(1004, "橋下カブト", 1, 3, "beetle1", "教育委員会の糞やろう", "攻撃力アップ",2);
		
		
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
		BattleUseKit.setUseKit(context, BattleUseKit.DeckNum.DECK1, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(2l);						// 虫キットID
		kit.setBarcode_id(299911116767l);			// バーコードID
		kit.setName("クワガタLv1");					// 名前
		kit.setAttack(500);							// 攻撃
		kit.setDefense(600);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kuwagata01");			// 画像ファイル名
		kit.setIntroduction("独島はわが領土");			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(context, BattleUseKit.DeckNum.DECK2, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(3l);						// 虫キットID
		kit.setBarcode_id(388711111111l);			// バーコードID
		kit.setName("イモムシ");						// 名前
		kit.setAttack(400);							// 攻撃
		kit.setDefense(400);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("imomusi01");			// 画像ファイル名
		kit.setIntroduction("アメリカン")	;			// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(context, BattleUseKit.DeckNum.DECK3, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(4l);						// 虫キットID
		kit.setBarcode_id(411111118989l);			// バーコードID
		kit.setName("兜ムシ");						// 名前
		kit.setAttack(800);							// 攻撃
		kit.setDefense(700);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto001");			// 画像ファイル名
		kit.setIntroduction("ジロジロみるなよ");				// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(context, BattleUseKit.DeckNum.DECK4, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(5l);						// 虫キットID
		kit.setBarcode_id(566711118889l);			// バーコードID
		kit.setName("兜ムシⅡ");						// 名前
		kit.setAttack(600);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto002");			// 画像ファイル名
		kit.setIntroduction("仲良し♪");				// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(context, BattleUseKit.DeckNum.DECK5, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(6l);						// 虫キットID
		kit.setBarcode_id(598711118888l);			// バーコードID
		kit.setName("ミヤマクワガタ");					// 名前
		kit.setAttack(800);							// 攻撃
		kit.setDefense(500);						// 守備
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
		kit.setEffect("攻撃力２倍");					// 効果
		kit.setBreedcount(4);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle1");			// 画像ファイル名
		kit.setIntroduction("トガッテルぜ～");				// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(2);							// 特殊効果ID
		factory.insertBeetleKitToDB(kit);
		// 戦闘時使用特殊カードに設定
		BattleUseSpecialCard.setUseKit(context, BattleUseSpecialCard.CardNum.CARD1, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(1002l);					// 虫キットID
		kit.setBarcode_id(111111111112l);			// バーコードID
		kit.setName("カブトガニ");						// 名前
		kit.setEffect("守備力２倍");					// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle2");			// 画像ファイル名
		kit.setIntroduction("触るとイタイヨ");			// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(3);							// 特殊効果ID
		factory.insertBeetleKitToDB(kit);
		// 戦闘時使用特殊カードに設定
		BattleUseSpecialCard.setUseKit(context, BattleUseSpecialCard.CardNum.CARD2, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(1003l);					// 虫キットID
		kit.setBarcode_id(111111111113l);			// バーコードID
		kit.setName("超蝶々");							// 名前
		kit.setEffect("相手攻撃力１／２");					// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle3");			// 画像ファイル名
		kit.setIntroduction("美しすぎる・・・");			// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(4);							// 特殊効果ID
		factory.insertBeetleKitToDB(kit);
		// 戦闘時使用特殊カードに設定
		BattleUseSpecialCard.setUseKit(context, BattleUseSpecialCard.CardNum.CARD3, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(1004l);					// 虫キットID
		kit.setBarcode_id(111111111114l);			// バーコードID
		kit.setName("ナナホシテントウ");					// 名前
		kit.setEffect("相手守備力１／２");				// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle3");			// 画像ファイル名
		kit.setIntroduction("七つのホシを持つ男");		// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(5);							// 特殊効果ID
		factory.insertBeetleKitToDB(kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(1005l);					// 虫キットID
		kit.setBarcode_id(111111111114l);			// バーコードID
		kit.setName("女王蜂");					// 名前
		kit.setEffect("ＬＰ ３０％回復");				// 効果
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle3");			// 画像ファイル名
		kit.setIntroduction("ロイヤルゼリーもってます");		// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(1);							// 特殊効果ID
		factory.insertBeetleKitToDB(kit);

		// 戦闘時使用特殊カードに設定
//		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD1, kit);

		
		// ユーザ情報クラスのアクセス例
//		StatusInfo.setUserName(this, "Testユーザ名");
		StatusInfo.setLP(context, Integer.parseInt((String)context.getText(R.string.maxLp)));
		StatusInfo.setLevel(context, 1);
		StatusInfo.setButtleCount(context, 123);
		StatusInfo.setEXP(context, 999);

		Log.d("★★", "Name　：　" + StatusInfo.getUserName(context));
		Log.d("★★", "LP　：　" + StatusInfo.getLP(context));
		Log.d("★★", "Lv　：　" + StatusInfo.getLevel(context));
		Log.d("★★", "count　：　" + StatusInfo.getButtleCount(context));
		Log.d("★★", "EXP　：　" + StatusInfo.getEXP(context));


		// 戦闘時使用キットクラスの使用例
		BeetleKit beetlekit1 = BattleUseKit.getUseKit(context, BattleUseKit.DeckNum.DECK1);
		BeetleKit beetlekit2 = BattleUseKit.getUseKit(context, BattleUseKit.DeckNum.DECK2);
		BeetleKit beetlekit3 = BattleUseKit.getUseKit(context, BattleUseKit.DeckNum.DECK3);
		BeetleKit beetlekit4 = BattleUseKit.getUseKit(context, BattleUseKit.DeckNum.DECK4);
		BeetleKit beetlekit5 = BattleUseKit.getUseKit(context, BattleUseKit.DeckNum.DECK5);

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
		BeetleKit specialkit1 = BattleUseSpecialCard.getUseKit(context, BattleUseSpecialCard.CardNum.CARD1);
		BeetleKit specialkit2 = BattleUseSpecialCard.getUseKit(context, BattleUseSpecialCard.CardNum.CARD2);
		BeetleKit specialkit3 = BattleUseSpecialCard.getUseKit(context, BattleUseSpecialCard.CardNum.CARD3);

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
