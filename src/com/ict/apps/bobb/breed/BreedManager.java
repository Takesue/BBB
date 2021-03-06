package com.ict.apps.bobb.breed;

import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.common.BeetleKitFactory;
import com.ict.apps.bobb.data.BeetleKit;
import com.ict.apps.bobb.data.CardImageInfo;
import com.ict.apps.bobb.db.BoBBDBHelper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

/**
 * ブリード全般の機能クラス
 */
public class BreedManager {
	
	
	/**
	 * バーコード情報から虫キットを生成する。
	 * @param barcode
	 * @return
	 */
	public BeetleKit generateCardStatusFromBarcode(Context context, long barcode) {
		
		// ★既に読み込んだバーコードかどうかチェック
		if(this.existBarcode(context, barcode)) {
			Toast.makeText(context, "既に同じバーコードで虫キットを取得しています。\n" +
					"同じバーコードは使用できません。", Toast.LENGTH_SHORT).show();
			return null;
		}
		
		BeetleKit bk = null;
		// ★決め打ちの事前登録済みのバーコードIDでないか？
		bk = this.createNewSpeBeetleKit(context, barcode);

		if (bk == null) {
			// 決め打ちで無い場合、一般カードの生成
			bk = createNewBeetleKit(context, barcode);
		}
		
		return bk;
	}

	private BeetleKit createNewSpeBeetleKit(Context context, long barcode) {
		
		String[] barcodeList = context.getResources().getStringArray(R.array.special_barcode);
		int[] imageIdList = context.getResources().getIntArray(R.array.special_imageid);
		
		BeetleKit bk = null;

		for (int i = 0; i < barcodeList.length; i++) {
			
			if (barcode == Long.parseLong(barcodeList[i])) {
				int imageId = imageIdList[i];

				// imageIDをキーにDBアクセスして、説明を取得する。
				BeetleKitFactory factory = new BeetleKitFactory(context);
				CardImageInfo imageInfo = factory.getImageInfo(imageId);

				// 虫キットインスタンスに値を設定する。
				bk = new BeetleKit();
				bk.setBeetleKitId(this.createBeetleId());
				bk.setName(imageInfo.getName());
				bk.setEffect(imageInfo.getEffect());
				bk.setIntroduction(imageInfo.getIntroduction());
				bk.setImage_id(imageId);
				bk.setBarcode_id(barcode);
				bk.setBreedcount(0);
				bk.setImageFileName(imageInfo.getFileName());
				bk.setType(2);
			}
		}

		return bk;
	}
	
	/**
	 * バーコードから一般カードの生成
	 * @param context
	 * @param barcode
	 * @return
	 */
	private BeetleKit createNewBeetleKit(Context context, long barcode) {

		// 決め打ちで無い場合、一般カードなので、攻守力の算出
		// バーコードから攻撃力算出
		int attack = this.getAttack(barcode);
		// バーコードから攻撃力算出
		int defense = this.getDefense(barcode);

		// 攻守の値からレベルを算出
		int level = this.getLevel(attack, defense);
		// 攻守の値からカテゴリを算出
		int category = this.getCategory(attack, defense);
		
		// レベルとカテゴリからImageIDを算出
		int imageId = this.getImageId(context, level, category);
		
		// imageIDをキーにDBアクセスして、説明を取得する。
		BeetleKitFactory factory = new BeetleKitFactory(context);
		CardImageInfo imageInfo = factory.getImageInfo(imageId);

		// 虫キットインスタンスに値を設定する。
		BeetleKit bk = new BeetleKit();
		bk.setBeetleKitId(this.createBeetleId());
		bk.setImage_id(imageId);
		bk.setBarcode_id(barcode);
		bk.setAttack(attack);
		bk.setDefense(defense);
		bk.setBreedcount(0);
		bk.setEffect(imageInfo.getEffect());
		bk.setIntroduction(imageInfo.getIntroduction());
		bk.setName(imageInfo.getName());
		bk.setImageFileName(imageInfo.getFileName());
		bk.setType(1);
		
		return bk;
	}

	/**
	 * バーコード履歴にバーコードを登録する
	 * @return
	 */
	public void insertBarcodeToDB(Context context, long barcode) {
		
		// DBインスタンス取得
		BoBBDBHelper beetleDb = BoBBDBHelper.getInstance(context);
		beetleDb.insertBarcodeReadInfo(barcode);
		
	}
	
	/**
	 * バーコード履歴に存在するバーコードかどうか確認する
	 * バーコードが履歴に既に存在する場合、trueを返却する。
	 * @param context
	 * @return
	 */
	private boolean existBarcode(Context context, long barcode) {
		
		boolean flag = false;
		
		// DBインスタンス取得
		BoBBDBHelper beetleDb = BoBBDBHelper.getInstance(context);
		
		// DBインスタンスオープン
		beetleDb.connectInstance();
		
		Cursor cursor = beetleDb.getBarcodeInfo();
		if(cursor != null) {
			//　カーソルがあった場合、虫キットインスタンスに情報を設定する。
			if (cursor.moveToFirst()) {
				// 取得したレコード数を取得する
				int iRecCnt = cursor.getCount();
				for (int i = 0; i < iRecCnt; i++) {
					
					// 取得レコードのバーコードと比較
					if (barcode == cursor.getLong(0)) {
						// バーコードが一した場合true
						flag = true;
					}

					cursor.moveToNext();
				}
			}
			cursor.close();
		}
		
		// DBインスタンスクローズ
		beetleDb.closeInstance();
		
		return flag;
	}
	
	/**
	 * バーコード情報から値を算出
	 * @param barcode
	 * @param ptype
	 * @return
	 */
	private int calculateCardValue(long barcode, int ptype) {
		
		int retValue = 0;

		if (0 < barcode) {
			// 13桁の文字列に変換
			String strBarcode = String.format("%1$013d", barcode);
			// 桁を配列に1文字づつ格納
			String[] digitnum = new String[13];
			for (int i = 0; i < digitnum.length; i++) {
				digitnum[i] = strBarcode.substring(i, i+1);
				System.out.println(digitnum[i]);
			}

			int base = 1;
			int typeA = Integer.valueOf(digitnum[1] + digitnum[12]) + base;
			int typeB = Integer.valueOf(digitnum[11] + digitnum[2]) + base;
			int typeC = Integer.valueOf(digitnum[3] + digitnum[10]) + base;
			int typeD = Integer.valueOf(digitnum[9] + digitnum[4]) + base;
			int typeE = Integer.valueOf(digitnum[5] + digitnum[8]) + base;
			int typeF = Integer.valueOf(digitnum[7] + digitnum[6]) + base;
			
			// 値は400～2400の範囲で収束する
			
			int attack = ((typeA + typeD)*typeE/1000*100) + 400;
			int defense = ((typeB + typeF)*typeC/1000*100) + 400;
			
			System.out.println(attack);
			System.out.println(defense);
			
			// 高い値が出た場合、割合を下げる
			int total = 0;
			for (int i = 0; i < digitnum.length; i++) {
				total += Integer.valueOf(digitnum[i]);
			}
			
			if (attack > 1500) {
				attack = attack*(10 - (total % 5))/10;
			}
			if (defense > 1500) {
				defense = defense*(10 -(total % 5))/10;
			}
			
			retValue = ((ptype == 0) ? attack : defense);
		}
		
		
		return retValue;
	}

	/**
	 * 攻撃力取得
	 * @param barcode
	 * @return
	 */
	private int getAttack(long barcode) {
		return this.calculateCardValue(barcode, 0);
	}

	/**
	 * 守備力取得
	 * @param barcode
	 * @return
	 */
	private int getDefense(long barcode) {
		return this.calculateCardValue(barcode, 1);
	}

	/**
	 * カードレベル取得
	 * @param attack
	 * @param defense
	 * @return
	 */
	private int getLevel(int attack, int defense) {
		// xmlに切り出して動的に変更できるようにしたい。
		int lv0_max = 0;
		int lv1_max = 2000;
		int lv2_max = 3000;
		int lv3_max = 4000;
		
		int level = 0;
		
		int total = attack + defense;
		
		if (lv0_max < total && total <= lv1_max) {
			// レベル１
			level = 1;
		}
		else if (lv1_max < total && total <= lv2_max) {
			// レベル２
			level = 2;
		}
		else if (lv2_max < total && total <= lv3_max) {
			// レベル３
			level = 3;
		}
		else {
			level = 3;
		}
		
		return level;
	}

	/**
	 * 種別取得
	 * @param attack
	 * @param defense
	 * @return
	 */
	private int getCategory(int attack, int defense) {
		int category = 0;
		
		// 比率
		int rate = 2;
		
		if (defense * rate <= attack) {
			// 攻撃型
			category = 1;
		}
		else if (attack * rate <= defense) {
			// 守備型
			category = 3;
		}
		else {
			// バランス型
			category = 2;
		}
		
		return category;
	}

	/**
	 * 画像ID取得
	 * @param level
	 * @param category
	 * @return
	 */
	private int getImageId(Context context, int level, int category) {

//		// 画像IDのマッピング
//		int[][] imageid_map = {
//				{1,2,3},
//				{4,5,6},
//				{7,8,9}
//		};
		
		// 画像テーブルからマッチするイメージを取得する
		
		// DBインスタンス取得
		BoBBDBHelper beetleDb = BoBBDBHelper.getInstance(context);
		
		// DBインスタンスオープン
		beetleDb.connectInstance();
		
		int imageId = 0;
		Cursor cursor = beetleDb.getCardImageInfo(level, category);
		if(cursor != null) {
			//　カーソルがあった場合、虫キットインスタンスに情報を設定する。
			if (cursor.moveToFirst()) {
				// 取得したレコード数を取得する
				int iRecCnt = cursor.getCount();
				for (int i = 0; i < iRecCnt; i++) {
					
					imageId = cursor.getInt(beetleDb.getImageColIndex(BoBBDBHelper.CARD_IMAGE_IMAGE_ID));
					
					cursor.moveToNext();
				}
			}
			cursor.close();
		}
		
		// DBインスタンスクローズ
		beetleDb.closeInstance();
		
		Log.d("★", "level: " + level + "  category: " + category + "  imageid: " + imageId);
		
		return imageId;
//		return imageid_map[level-1][category-1];
	}

	/**
	 * 虫キットIDを生成
	 * @return
	 */
	private long createBeetleId() {
		// とりあえずミリ秒単位の時間をIDにしておく。Web対応する際に、もう一度検討する。
		return System.currentTimeMillis();
	}
	
	
	/**
	 * DBに登録します。
	 * @param kit
	 */
	public void registBeetleKit(Context context, BeetleKit kit) {
		BeetleKitFactory factory = new BeetleKitFactory(context);
		factory.insertBeetleKitToDB(kit);
		
	}

	/**
	 * 交配
	 * @param beetle1
	 * @param beetle2
	 * @return
	 */
	public BeetleKit breedBeetle(Context context, BeetleKit beetle1, BeetleKit beetle2) {
		
		// ★生成ロジックはとりあえずの状態、バランスを考えた場合、再検討必須。
		
		// 生まれた虫キット
		BeetleKit newBeetle = null;
		
		// バーコードを単純に足して2で割る
		long newBarcode = (beetle1.getBarcode_id() + beetle2.getBarcode_id())/2;
		
		newBeetle = this.generateCardStatusFromBarcode(context, newBarcode);
		
		// 新カードのブリード回数設定
		// 2枚のうちブリード回数が多い方の値を＋１
		if (beetle1.getBreedcount() <= beetle2.getBreedcount()) {
			newBeetle.setBreedcount(beetle2.getBreedcount() + 1);
			
		}
		
		// 基礎値×ブリード回数の合計が加算される
		int base = 20;
		newBeetle.setAttack(newBeetle.getAttack() + ((beetle1.getBreedcount() + beetle2.getBreedcount()) * base) + 200);
		newBeetle.setDefense(newBeetle.getDefense() + ((beetle1.getBreedcount() + beetle2.getBreedcount()) * base) + 200);
		
		// ★親のバーコードは削除するのか？
		
		// 親カード削除
		BeetleKitFactory factory = new BeetleKitFactory(context);
		factory.deleteBeetleKit(beetle1.getBeetleKitId());
		factory.deleteBeetleKit(beetle2.getBeetleKitId());
		
		// 子供カード登録
		factory.insertBeetleKitToDB(newBeetle);
		

		return newBeetle;
	}

}
