package com.ict.apps.bobb.db;

import com.ict.apps.bobb.data.BeetleKit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * BoBB DB接続クラス
 *
 */
public class BoBBDBHelper extends SQLiteOpenHelper{

	public SQLiteDatabase db = null;

	// シングルトン　自インスタンス保持
	private static BoBBDBHelper instance = null;
	// DB Name
	private static final String name = "bobb.db";
	// DB version
	private static final int version = 1;
	private static final CursorFactory factory = null;

	/* テーブル名 */
	private static final String BEETLE_KIT_INFO_TBL = "BEETLE_KIT_INFO_TBL";
	private static String CARD_IMAGE_TBL = "CARD_IMAGE_TBL";
	private static String READ_BARCODE_TBL = "READ_BARCODE_TBL";
	
	// カラム名　虫キット情報テーブル
	public static final String BEETLE_KIT_BEETLE_ID = "beetle_id";
	public static final String BEETLE_KIT_BARCODE_ID = "barcode_id";
	public static final String BEETLE_KIT_IMAGE_ID = "image_id";
	public static final String BEETLE_KIT_FILENAME = "file_name";
	public static final String BEETLE_KIT_NAME = "name";
	public static final String BEETLE_KIT_ATTACK = "attack";
	public static final String BEETLE_KIT_DEFENCE = "defence";
	public static final String BEETLE_KIT_BREEDCOUNT = "breedcount";
	public static final String BEETLE_KIT_TYPE = "type";
	public static final String BEETLE_KIT_INTRODUCTION = "introduction";
	public static final String BEETLE_KIT_EFFECT = "effect";

	// カラム名　カード画像テーブル
	public static final String CARD_IMAGE_IMAGE_ID = "iname_id";
	public static final String CARD_IMAGE_NAME = "name";
	public static final String CARD_IMAGE_CATEGORY = "category";
	public static final String CARD_IMAGE_LEVEL = "level";
	public static final String CARD_IMAGE_FILENAME = "file_name";
	public static final String CARD_IMAGE_INTRODUCTION = "introduction";
	public static final String CARD_IMAGE_EFFECT = "effect";
	
	// カラム名　バーコード読み込み履歴テーブル
	public static final String READ_BARCODE = "barcode";

	// 検索時取得カラム　虫キット情報テーブル
	private static final String[] BEETLE_KIT_COLS = {
		BoBBDBHelper.BEETLE_KIT_BEETLE_ID,
		BoBBDBHelper.BEETLE_KIT_BARCODE_ID,
		BoBBDBHelper.BEETLE_KIT_IMAGE_ID,
		BoBBDBHelper.BEETLE_KIT_FILENAME,
		BoBBDBHelper.BEETLE_KIT_NAME,
		BoBBDBHelper.BEETLE_KIT_ATTACK,
		BoBBDBHelper.BEETLE_KIT_DEFENCE,
		BoBBDBHelper.BEETLE_KIT_BREEDCOUNT,
		BoBBDBHelper.BEETLE_KIT_TYPE,
		BoBBDBHelper.BEETLE_KIT_INTRODUCTION,
		BoBBDBHelper.BEETLE_KIT_EFFECT
	};
	
	// 検索時取得カラム　カード画像テーブル
	private static final String[] CARD_IMAGE_COLS = {
		BoBBDBHelper.CARD_IMAGE_IMAGE_ID,
		BoBBDBHelper.CARD_IMAGE_NAME,
		BoBBDBHelper.CARD_IMAGE_CATEGORY,
		BoBBDBHelper.CARD_IMAGE_LEVEL,
		BoBBDBHelper.CARD_IMAGE_FILENAME,
		BoBBDBHelper.CARD_IMAGE_INTRODUCTION,
		BoBBDBHelper.CARD_IMAGE_EFFECT
	};

	// 検索時取得カラム　バーコード読み込み履歴テーブル
	private static final String[] BARCODE_COLS = {
		BoBBDBHelper.READ_BARCODE
	};

	
	/**
	 * コンストラクタ
	 * @param context
	 */
	private BoBBDBHelper(Context context) {
		super(context, BoBBDBHelper.name, BoBBDBHelper.factory, BoBBDBHelper.version);
	}
	
	/**
	 * BoBBDBHelperのインスタンスを取得する
	 * @param context
	 * @return
	 */
	public static synchronized BoBBDBHelper getInstance(Context context) {
		
		if (BoBBDBHelper.instance == null) {
			BoBBDBHelper.instance = new BoBBDBHelper(context);
		}
		return BoBBDBHelper.instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		/* 虫キット情報テーブル*/
		String sql1 = "CREATE TABLE " + BoBBDBHelper.BEETLE_KIT_INFO_TBL + " ( "
				+ BoBBDBHelper.BEETLE_KIT_BEETLE_ID + " INTEGER PRIMARY KEY ,"	// 虫キットID
				+ BoBBDBHelper.BEETLE_KIT_BARCODE_ID + " INTEGER, "				// バーコードID
				+ BoBBDBHelper.BEETLE_KIT_IMAGE_ID + " INTEGER ,"				// 画像ID
				+ BoBBDBHelper.BEETLE_KIT_FILENAME + " TEXT ,"					// ファイル名
				+ BoBBDBHelper.BEETLE_KIT_NAME + " TEXT, "						// 名前
				+ BoBBDBHelper.BEETLE_KIT_ATTACK + " INTEGER, "					// 攻撃力
				+ BoBBDBHelper.BEETLE_KIT_DEFENCE + " INTEGER, "				// 防御力
				+ BoBBDBHelper.BEETLE_KIT_BREEDCOUNT + " INTEGER, "				// 対戦使用回数
				+ BoBBDBHelper.BEETLE_KIT_TYPE + " INTEGER, "					// タイプ (一般、特殊)
				+ BoBBDBHelper.BEETLE_KIT_INTRODUCTION + " TEXT, "				// 説明文
				+ BoBBDBHelper.BEETLE_KIT_EFFECT + " TEXT);";					// 特殊効果説明
		db.execSQL(sql1);

		/* カード画像テーブル */
		String sql2 = "CREATE TABLE " + BoBBDBHelper.CARD_IMAGE_TBL + " ( "
				+ BoBBDBHelper.CARD_IMAGE_IMAGE_ID + " INTEGER PRIMARY KEY, "	// 画像ID
				+ BoBBDBHelper.CARD_IMAGE_NAME + " TEXT, "						// 名前
				+ BoBBDBHelper.CARD_IMAGE_CATEGORY + " INTEGER, "				// 種別（攻撃型、バランス型、守備型）
				+ BoBBDBHelper.CARD_IMAGE_LEVEL + " INTEGER, "					// レベル
				+ BoBBDBHelper.CARD_IMAGE_FILENAME + " TEXT, "					// ファイル名
				+ BoBBDBHelper.CARD_IMAGE_INTRODUCTION + " TEXT, "				// 説明文
				+ BoBBDBHelper.CARD_IMAGE_EFFECT + " TEXT);";					// 特殊効果説明
		db.execSQL(sql2);
		
		/* バーコード読み込みテーブル */
		String sql3 = "CREATE TABLE " + BoBBDBHelper.READ_BARCODE_TBL + " ( "
				+ BoBBDBHelper.READ_BARCODE + " INTEGER PRIMARY KEY);";			// バーコード情報
		db.execSQL(sql3);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
	
	/**
	 * 虫キット情報のインサート
	 * @param kit
	 */
	public void insertBeetleKit(BeetleKit kit) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(BoBBDBHelper.BEETLE_KIT_BEETLE_ID, kit.getBeetleKitId());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_BARCODE_ID, kit.getBarcode_id());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_IMAGE_ID, kit.getImage_id());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_FILENAME, kit.getImageFileName());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_NAME, kit.getName());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_ATTACK, kit.getAttack());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_DEFENCE, kit.getDefence());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_BREEDCOUNT, kit.getBreedcount());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_TYPE, kit.getType());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_INTRODUCTION, kit.getIntroduction());
		contentValues.put(BoBBDBHelper.BEETLE_KIT_EFFECT, kit.getEffect());
		
		db.insertOrThrow(BoBBDBHelper.BEETLE_KIT_INFO_TBL, null, contentValues);
		db.close();
	}
	
	/**
	 * カード画像情報のインサート
	 * @param id 画像ID
	 * @param name　画像名
	 * @param category　攻撃型　バランス型　守備型
	 * @param level　画像レベル
	 * @param filename　ファイル名
	 */
	public void insertCardImageInfo(int id, String name, int category, int level, String filename, String intro, String effect) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(BoBBDBHelper.CARD_IMAGE_IMAGE_ID, id);
		contentValues.put(BoBBDBHelper.CARD_IMAGE_NAME, name);
		contentValues.put(BoBBDBHelper.CARD_IMAGE_CATEGORY, category);
		contentValues.put(BoBBDBHelper.CARD_IMAGE_LEVEL, level);
		contentValues.put(BoBBDBHelper.CARD_IMAGE_FILENAME, filename);
		contentValues.put(BoBBDBHelper.CARD_IMAGE_INTRODUCTION, intro);
		contentValues.put(BoBBDBHelper.CARD_IMAGE_EFFECT, effect);
		
		db.insertOrThrow(BoBBDBHelper.CARD_IMAGE_TBL, null, contentValues);
		db.close();
	}

	/**
	 * バーコード読み込み履歴のインサート
	 * @param barcode 読み込みバーコード
	 */
	public void insertBarcodeReadInfo(int barcode) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(BoBBDBHelper.READ_BARCODE, barcode);
		
		db.insertOrThrow(BoBBDBHelper.READ_BARCODE_TBL, null, contentValues);
		db.close();
	}

	/**
	 * データベースオープン
	 */
	public void connectInstance(){
		this.db = this.getReadableDatabase();
	}
	
	/**
	 * データベースクローズ
	 */
	public void closeInstance(){
		this.db.close();
	}

	
	/**
	 * 虫キット情報取得
	 * @param 種別　key 一般カードのキット：1　特殊効果カードのキット：2
	 * @return
	 */
	public Cursor getBeetleKitInfoList(int key) {
		Cursor cursor = db.query(BoBBDBHelper.BEETLE_KIT_INFO_TBL, BoBBDBHelper.BEETLE_KIT_COLS,
				BoBBDBHelper.BEETLE_KIT_TYPE + "=" + key, null, null, null, null);
		return cursor;
	}
	
	/**
	 * 虫キットIDをキーにして情報取得
	 * @param 虫キットID
	 * @return
	 */
	public Cursor getBeetleKitInfoForId(long beetleid) {
		Cursor cursor= db.query(BoBBDBHelper.BEETLE_KIT_INFO_TBL, BoBBDBHelper.BEETLE_KIT_COLS,
				BoBBDBHelper.BEETLE_KIT_BEETLE_ID + "=" + beetleid, null, null, null, null);
		return cursor;
	}

	/**
	 * カード画像情報取得
	 * @param 画像ID
	 * @return
	 */
	public Cursor getCardImageInfo(int imageId) {
		Cursor cursor= db.query(BoBBDBHelper.CARD_IMAGE_TBL, BoBBDBHelper.CARD_IMAGE_COLS,
				BoBBDBHelper.CARD_IMAGE_IMAGE_ID + "=" + imageId, null, null, null, null);
		return cursor;
	}
	
	/**
	 * バーコード読み込み履歴取得
	 * @param 画像ID
	 * @return
	 */
	public Cursor getBarcodeInfo() {
		Cursor cursor= db.query(BoBBDBHelper.READ_BARCODE_TBL, BoBBDBHelper.BARCODE_COLS, null, null, null, null, null);
		return cursor;
	}
	
	/**
	 * 虫キットIDをキーにデータを削除
	 * @param key 虫キットID
	 */
	public void deleteBeetleKitInfo(long key) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(BoBBDBHelper.BEETLE_KIT_INFO_TBL, BoBBDBHelper.BEETLE_KIT_BEETLE_ID + "=" + key, null);
		db.close();
	}
	
	/**
	 * バーコードをキーにバーコード読み込み履歴を削除
	 * @param key バーコード
	 */
	public void deleteBarcodeInfo(long key) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(BoBBDBHelper.READ_BARCODE_TBL, BoBBDBHelper.READ_BARCODE + "=" + key, null);
		db.close();
	}
	
	/**
	 * DB上の全情報を削除
	 */
	public void truncateAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(BoBBDBHelper.BEETLE_KIT_INFO_TBL, null, null);
		db.delete(BoBBDBHelper.CARD_IMAGE_TBL, null, null);
		db.delete(BoBBDBHelper.READ_BARCODE_TBL, null, null);
		db.close();
	}
	
	/**
	 * 虫キット検索番号を取得
	 * @param name
	 * @return
	 */
	public int getKitColIndex(String name) {
		
		int pos = 0;
		for (int i = 0; i < BoBBDBHelper.BEETLE_KIT_COLS.length; i++) {
			if (BoBBDBHelper.BEETLE_KIT_COLS[i].equals(name)) {
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * 画像情報検索番号を取得
	 * @param name
	 * @return
	 */
	public int getImageColIndex(String name) {
		
		int pos = 0;
		for (int i = 0; i < BoBBDBHelper.CARD_IMAGE_COLS.length; i++) {
			if (BoBBDBHelper.CARD_IMAGE_COLS[i].equals(name)) {
				pos = i;
			}
		}
		return pos;
	}


}
