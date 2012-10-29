package com.ict.apps.bobb.data;

import android.content.Context;

/**
 * カブト虫キットクラス
 *
 */
public class BeetleKit {
	
	// 虫キットID
	private long beetleKitId = 0;
	// バーコードID
	private long barcode_id = 0;
	// 画像ID
	private int image_id = 0;
	// 画像ファイル名
	private String imageFileName = null;
	// 固有の名前
	private String name = null;
	// 攻撃力
	private int attack = 0;
	// 防御力
	private int defence = 0;
	// ブリード回数
	private int breedcount = 0;
	// タイプ（一般 = 1 or 特殊 = 2）
	private int type = 0;
	// 説明文
	private String introduction = null;
	// 特殊効果説明
	private String effect = null;

	/**
	 * コンストラクタ
	 */
	public BeetleKit() {
	}

	public long getBeetleKitId() {
		return beetleKitId;
	}

	public void setBeetleKitId(long beetleKitId) {
		this.beetleKitId = beetleKitId;
	}

	public long getBarcode_id() {
		return barcode_id;
	}

	public void setBarcode_id(long barcode_id) {
		this.barcode_id = barcode_id;
	}

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getBreedcount() {
		return breedcount;
	}

	public void setBreedcount(int encount) {
		this.breedcount = encount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	/**
	 * 虫キットからカードインスタンスを生成します。
	 * 通常カードのキットからは虫カードインスタンスを６つ
	 * 特殊カードのキットからは１つ
	 * @return Cardクラスの配列
	 */
	public Card[] createBeetleCards() {
		
		Card[] cards = null;
		
		// 一般カードの場合
		if(this.type == 1) {
			cards = new Card[6];
			
			for(int i = 0; i < cards.length; i++) {
				BeetleCard bc = new BeetleCard();
				bc.setValues(this);
				
				if (i <= 2) {
					// 攻撃カード
					bc.setTypeAttack();
				}
				else {
					// 守備カード
					bc.setTypeDefence();
				}
				
				if (i % 3 == 0) {
					// 火属性カード
					bc.setAttribute(CardAttribute.FIRE);
				}
				else if (i % 3 == 1) {
					// 水属性カード
					bc.setAttribute(CardAttribute.WATER);
				}
				else if (i % 3 == 2) {
					// 風属性カード
					bc.setAttribute(CardAttribute.WIND);
				}
				
				cards[i] = bc;
			}
			
			
		}
		// 特殊カードの場合
		if(this.type == 2) {
			cards = new Card[1];

			// 特殊カード
			cards[0] = new SpecialCard();
			cards[0].setValues(this);
		}
		
		return cards;
	}
	
	/**
	 * 保持している画像のリソースIDを取得する
	 * @param context
	 * @return
	 */
	public int getImageResourceId(Context context) {
		
		String defType = "drawable";
		
		// リソースIDを取得
		int target_resource_id = context.getResources().getIdentifier(
				this.getImageFileName(),
				defType,
				context.getPackageName()
			);
		return target_resource_id;
	}

	
}
