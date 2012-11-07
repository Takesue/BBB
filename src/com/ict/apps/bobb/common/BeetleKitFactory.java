package com.ict.apps.bobb.common;

import java.util.ArrayList;

import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.CardImageInfo;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.content.Context;
import android.database.Cursor;


/**
 * 虫キット生成クラス
 * @author take
 *
 */
public class BeetleKitFactory {
	
	// BoBBDBHelperインスタンス
	private BoBBDBHelper beetleDb = null;
	
	/**
	 * 虫キット種別
	 */
	public enum KitType {
		NORMAL,		// 一般カードのキット
		SPECIAL		// 特殊カードのキット
	}

	
	/**
	 * コンストラクタ
	 * @param context コンテキスト
	 */
	public BeetleKitFactory(Context context) {
		// BoBBDBHelperインスタンスを取得
		this.beetleDb = BoBBDBHelper.getInstance(context);
	}

	/**
	 * カーソル情報を元に虫キットインスタンスを生成し、Array配列で返却する
	 * @param cursor カーソル
	 * @return
	 */
	private ArrayList<BeetleKit> getBeetleKitList(Cursor cursor) {

		ArrayList<BeetleKit> kitList = new ArrayList<BeetleKit>();
		
		if(cursor != null) {
			//　カーソルがあった場合、虫キットインスタンスに情報を設定する。
			if (cursor.moveToFirst()) {
				// 取得したレコード数を取得する
				int iRecCnt = cursor.getCount();
				for (int i = 0; i < iRecCnt; i++) {
					
					BeetleKit kit = new BeetleKit();
					
					kit.setBeetleKitId(cursor.getLong(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_BEETLE_ID)));
					kit.setBarcode_id(cursor.getLong(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_BARCODE_ID)));
					kit.setImage_id(cursor.getInt(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_IMAGE_ID)));
					kit.setImageFileName(cursor.getString(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_FILENAME)));
					kit.setName(cursor.getString(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_NAME)));
					kit.setAttack(cursor.getInt(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_ATTACK)));
					kit.setDefence(cursor.getInt(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_DEFENCE)));
					kit.setBreedcount(cursor.getInt(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_BREEDCOUNT)));
					kit.setType(cursor.getInt(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_TYPE)));
					kit.setIntroduction(cursor.getString(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_INTRODUCTION)));
					kit.setEffect(cursor.getString(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_EFFECT)));
					kit.setEffectId(cursor.getInt(this.beetleDb.getKitColIndex(BoBBDBHelper.BEETLE_KIT_EFFECT_ID)));
					kitList.add(kit);

					cursor.moveToNext();
				}
			}
			cursor.close();
		}
		return kitList;
	}
	
	/**
	 * 指定した虫キットIDの虫キットインスタンスを生成する
	 * @param beetleId
	 * @return
	 */
	public BeetleKit getBeetleKit(long beetleId) {

		this.beetleDb.connectInstance();
		
		// 虫キット情報をカーソル形式で取得
		Cursor cursor = this.beetleDb.getBeetleKitInfoForId(beetleId);
		
		ArrayList<BeetleKit> kitList = this.getBeetleKitList(cursor);

		this.beetleDb.closeInstance();

		return kitList.get(0);
	}

	/**
	 * 種別：虫キットインスタンスを取得
	 * @return
	 */
	public ArrayList<BeetleKit> getBeetleKit(KitType key) {
		
		this.beetleDb.connectInstance();
		Cursor cursor = this.beetleDb.getBeetleKitInfoList( key == BeetleKitFactory.KitType.NORMAL ? 1 : 2);
		ArrayList<BeetleKit> list = this.getBeetleKitList(cursor);
		this.beetleDb.closeInstance();
		
		return list;
	}
	
	
	/**
	 * カード画像情報
	 * @param imageId
	 * @return
	 */
	public CardImageInfo getImageInfo(int imageId) {
		
		CardImageInfo imageInfo = new CardImageInfo();

		this.beetleDb.connectInstance();
		Cursor cursor = this.beetleDb.getCardImageInfo(imageId);
		if(cursor != null) {
			//　カーソルがあった場合、虫キットインスタンスに情報を設定する。
			if (cursor.moveToFirst()) {
				
				// 取得したレコード数を取得する
				imageInfo.setInameId(cursor.getInt(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_IMAGE_ID)));
				imageInfo.setCategory(cursor.getInt(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_CATEGORY)));
				imageInfo.setLevel(cursor.getInt(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_LEVEL)));
				imageInfo.setName(cursor.getString(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_NAME)));
				imageInfo.setFileName(cursor.getString(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_FILENAME)));
				imageInfo.setIntroduction(cursor.getString(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_INTRODUCTION)));
				imageInfo.setEffect(cursor.getString(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_EFFECT)));
				imageInfo.setEffectId(cursor.getInt(this.beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_EFFECT_ID)));
			}
			cursor.close();
		}

		this.beetleDb.closeInstance();
		
		return imageInfo;
	}
	
	/**
	 * 虫キットインスタンスをDBへ格納
	 * @param kit
	 */
	public void insertBeetleKitToDB(BeetleKit kit) {
		this.beetleDb.insertBeetleKit(kit);
	}

	// ★バーコード読み込みから、虫キットインスタンスを生成
	//   画像IDテーブルは、インストール時か初期起動時にDBに入れ込む必要あり。
	public void setImageInfo(int id, String name, int category, int level, String filename, String intro, String effect) {
		
		this.beetleDb.insertCardImageInfo(id, name, category, level, filename, intro, effect, 1);
		
	}
	
	/**
	 * 指定した虫キットIDの虫キットを削除
	 * @param beetleId
	 */
	public void deleteBeetleKit(long beetleId) {
		this.beetleDb.deleteBeetleKitInfo(beetleId);
		
	}
	
	/**
	 * DBのデータ全削除
	 */
	public void deleteAllDbData() {
		this.beetleDb.truncateAll();
	}


}
