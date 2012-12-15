package com.ict.apps.bobb.bobbactivity;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gcm.GCMRegistrar;
import com.ict.apps.bobb.base.BaseActivity;
import com.ict.apps.bobb.battle.player.CPU01;
import com.ict.apps.bobb.battle.player.CPU02;
import com.ict.apps.bobb.battle.player.OnlinePlayer;
import com.ict.apps.bobb.battle.player.Player;
import com.ict.apps.bobb.battle.player.PlayerListAdapter;
import com.ict.apps.bobb.common.BattleUseKit;
import com.ict.apps.bobb.common.BattleUseSpecialCard;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.common.StatusInfo;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.Card;
import com.ict.apps.bobb.data.SpecialCard;
import com.ict.apps.bobb.db.BoBBDBHelper;
import com.ict.apps.bobb.online.OnlineQuery;
import com.ict.apps.bobb.online.OnlineQueryOnlineUserList;
import com.ict.apps.bobb.online.OnlineResponseListener;
import com.ict.apps.bobb.online.OnlineUtil;
import com.ict.apps.bobb.online.OnlineConnection;
import com.ict.apps.bobb.online.OnlineOneTimeTask;
import com.ict.apps.bobb.online.OnlinePoolingTask;
import com.ict.apps.bobb.online.OnlineQueryAccessLog;
import com.ict.apps.bobb.online.OnlineQueryUserRegister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class StartActivity extends BaseActivity {

//一回目のインなのかの判断で使用しているだけですので、後で正しく組み直してください。
	public Boolean count = false;
//ここまで
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);

        // 初回だけ呼び出す
		if (StatusInfo.getUserId(this).equals("")) {
			
	    	Intent intent;
    		intent = new Intent(this, UserInfoRegistrationActivity.class);
			this.startActivityForResult(intent, 0);
			
		}
		else {
			this.init();
		}
    }

    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				this.init();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// ブロードキャストの登録解除
		OnlineUtil.unregisterDevice(this);

	}

//	public void startOnClick(View v){
//    	
//    	Intent intent;
////    	if(this.count == true){
//    		intent = new Intent(StartActivity.this, MainMenuActivity.class);
////    	}else{
////    		intent = new Intent(StartActivity.this, UserInfoRegistrationActivity.class);
////    		this.count = true;
////    	}
//
////		Intent intent = new Intent(StartActivity.this, MainMenuActivity.class);
//		startActivity(intent);
//    }
//    
//    public void ruleOnClick(View v){
//    	
//		Intent intent = new Intent(StartActivity.this, RuleActivity.class);
//		startActivity(intent);
//		
//	}

    
    
	/**
	 * ブリーダーメニューボタンクリック時
	 * @param v
	 */
	public void breedersMenuOnClick(View v) {

		Intent aintent = new Intent(this, BreedersMenuActivity.class);
		startActivity(aintent);

	}

	/**
	 * CPU対戦ボタンクリック時
	 * @param v
	 */
	public void battleCpuOnClick(View v) {

		final Player[] userList = {
			new CPU01(this),
			new CPU02(this)
		};

		this.viewPopupPlayerLis(userList);
	}
	
	private OnlineQueryOnlineUserList query = null;

	/**
	 * オンライン対戦ボタンクリック時
	 * @param v
	 */
	public void battleHumanOnClick(View v) {

		// 通信して対戦相手のリストを取得要求を出す
		this.query = new OnlineQueryOnlineUserList();
		this.query.setUserId(StatusInfo.getUserId(this));
		this.query.setLevel(StatusInfo.getLevel(this));
		this.query.setListner(new OnlineResponseListener() {
			@Override
			public void response(Context context, OnlineQuery query, Integer result) {
				viewPopupUserLis();
			}
		});
		new OnlinePoolingTask(this).execute(this.query);
	}


	private ArrayList<Integer> index = new ArrayList<Integer>();

	/**
	 * ポップアップでオンラインユーザリストを表示する
	 */
	public void viewPopupUserLis() {
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		ArrayList<String> ids = new ArrayList<String>();
		String userId = StatusInfo.getUserId(this);
		
		int battleUserNum = this.query.getResponseRecordCount();
		for (int i = 0; i < battleUserNum; i++) {
			
			// 自端末のIDは除外
			if (userId.equals(this.query.getResponseData(i, "user_id"))) {
				continue;
			}
			

			int idIndex = ids.indexOf(this.query.getResponseData(i, "user_id"));
			if (idIndex != -1) {
				// 既出のIDは追加しない
				continue;
			}
			
			// 既出で無いユーザIDの場合リストに追加
			Player p = new OnlinePlayer(this);
			p.setName(this.query.getResponseData(i, "user_name"));
			p.setLevel(Integer.parseInt(this.query.getResponseData(i, "user_level")));
			p.setRequest(this.query.getResponseData(i, "battle_id"));
			
			playerList.add(p);
			index.add(i);
			ids.add(this.query.getResponseData(i, "user_id"));
		}
		
		Player[] items = new Player[playerList.size()];
		items = playerList.toArray(items);
		
		
		if (items.length == 0) {
			// 検索結果対象が0件の場合
			// 対戦相手がいない
			Toast.makeText(this, "オンラインに対戦相手が見当たりません", Toast.LENGTH_LONG).show();
			return;
		}
		
		// ポップアップリスト表示
		this.viewPopupPlayerLis(items);
		
	}
	
	
	
	/**
	 * ポップアップでユーザリストを表示する
	 */
	public void viewPopupPlayerLis(final Player[] userList) {
		
		//コンテキストからインフレータを取得
		LayoutInflater inflater = LayoutInflater.from(this.getBaseContext());

		//レイアウトXMLからビュー(レイアウト)をインフレート
		final View playerList = inflater.inflate(R.layout.player_list, null);
		
		PlayerListAdapter userListAdapter = new PlayerListAdapter(this, userList);
		
		
		final AlertDialog ad = new AlertDialog.Builder(this)
		.setView(playerList)
		.show();

		((TextView) playerList.findViewById(R.id.popupTitle)).setText("対戦CPU選択");
		((TextView) playerList.findViewById(R.id.popupCancel)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ×クリックでポップアップ閉じる
				ad.cancel();
			}
		});

		ListView listView = (ListView) playerList.findViewById(R.id.playerList);
		
		// アダプターを設定します
		listView.setAdapter(userListAdapter);
		
		// リストビューのアイテムがクリックされた時に呼び出されるコールバックリスナーを登録します
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// クリックされたアイテムを取得します
				Intent cintent = new Intent(StartActivity.this, BattleActivity.class);
				
				if ( userList[position] instanceof OnlinePlayer ) {
					cintent.putExtra("user_mode", "online");
					cintent.putExtra("user_id", query.getResponseData(index.get(position), "user_id"));
					cintent.putExtra("user_name", query.getResponseData(index.get(position), "user_name"));
					cintent.putExtra("registration_id", query.getResponseData(index.get(position), "transaction_id"));
					cintent.putExtra("battle_id", query.getResponseData(index.get(position), "battle_id"));
					
					// インデックスをクリア
					index.clear();
				}
				else {
					cintent.putExtra("user_mode", "cpu");
					cintent.putExtra("user_name", userList[position].getName());
				}
				
				// 次画面へ遷移
				startActivity(cintent);
				
				// リスト表示終了
				ad.cancel();

			}
		});

	}

    
    
    
	private Handler mHandlerAccessLogTask = new Handler();
	public void init() {
		
		// GCMへ端末情報を登録する
		OnlineUtil.registerDevice(this);
		
		// アクセスログの投入
		final Context context = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 10; i++) {
						final String regId = GCMRegistrar.getRegistrationId(context);
						Log.d("★", "registrationid = " + regId);
						if (!regId.equals("")) {
							
							mHandlerAccessLogTask.post(new Runnable() {
								public void run() {
									OnlineQueryAccessLog query = new OnlineQueryAccessLog();
									query.setUserId(StatusInfo.getUserId(context));
									query.setUserName(StatusInfo.getUserName(context));
									query.setLevel(StatusInfo.getLevel(context));
									query.setRegistrationId(regId);
									new OnlineOneTimeTask(context).execute(query);
								}
							});
							break;
						}
						Thread.sleep(2000);
						
						if (i == 9) {
							Log.d("★", " failure gettting registrationid");
						}
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

//		try {
//			String response = OnlineConnection.post(query);
//			Log.d("StartActivity", response);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		BeetleKitFactory factory = new BeetleKitFactory(this);

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
		kit.setName("兜ムシ");						// 名前
		kit.setAttack(1150);						// 攻撃
		kit.setDefense(700);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto001");			// 画像ファイル名
		kit.setIntroduction("ジロジロみるなよ");				// カード説明
		kit.setType(1);								// 種別　1：一般　2：特殊
		factory.insertBeetleKitToDB(kit);
		// デッキに設定
		BattleUseKit.setUseKit(this, BattleUseKit.DeckNum.DECK4, kit);

		kit = new BeetleKit();
		kit.setBeetleKitId(5l);						// 虫キットID
		kit.setBarcode_id(566711118889l);			// バーコードID
		kit.setName("兜ムシⅡ");					// 名前
		kit.setAttack(700);							// 攻撃
		kit.setDefense(900);						// 守備
		kit.setBreedcount(0);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("kabuto002");			// 画像ファイル名
		kit.setIntroduction("仲良し♪");				// カード説明
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
		kit.setEffect("攻撃力２倍");					// 効果
		kit.setBreedcount(4);						// ブリード回数
		kit.setImage_id(1);							// 画像ID
		kit.setImageFileName("beetle1");			// 画像ファイル名
		kit.setIntroduction("つよいよ");				// カード説明
		kit.setType(2);								// 種別　1：一般　2：特殊
		kit.setEffectId(2);							// 特殊効果ID
		factory.insertBeetleKitToDB(kit);
		// 戦闘時使用特殊カードに設定
		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD1, kit);

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
		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD2, kit);

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
		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD3, kit);

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

		// 戦闘時使用特殊カードに設定
//		BattleUseSpecialCard.setUseKit(this, BattleUseSpecialCard.CardNum.CARD1, kit);

		
		// ユーザ情報クラスのアクセス例
//		StatusInfo.setUserName(this, "Testユーザ名");
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
