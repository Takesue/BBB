package com.ict.apps.bobb.data;

import android.content.Context;

public class Card {

	// 虫キットID
	private long beetleKitId = 0;
	// 画像ID
	private int image_id = 0;
	// 画像ファイル名
	private String imageFileName = null;
	// 名前
	private String name = null;
	// タイプ（一般 = 1 or 特殊 = 2  攻撃=3　守備=4）
	private int type = 0;
	// 説明文
	private String introduction = null;
	
	/**
	 * 虫キットインスタンスを元にカード情報を設定
	 * @param bk
	 */
	public void setValues(BeetleKit bk) {
		this.setBeetleKitId(bk.getBeetleKitId());
		this.setImageId(bk.getImage_id());
		this.setName(bk.getName());
		this.setType(bk.getType());
		this.setIntroduction(bk.getIntroduction());
		this.setImageFileName(bk.getImageFileName());
	}
	
	

	public long getBeetleKitId() {
		return beetleKitId;
	}

	public void setBeetleKitId(long beetleKitId) {
		this.beetleKitId = beetleKitId;
	}

	public int getImageId() {
		return image_id;
	}

	public void setImageId(int image_id) {
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
