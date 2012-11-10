package com.ict.apps.bobb.data;

/**
 * カード画像情報クラス
 *
 */
public class CardImageInfo {

	
	// 画像ID
	private int iname_id;
	
	// 名前
	private String name;
	
	// 種別　攻撃型(1)、バランス型(2)、守備型(3)
	private int category;
	
	// レベル １～３
	private int level;
	
	// ファイル名
	private String file_name;

	// 説明
	private String introduction;

	// 効果
	private String effect;
	
	// 効果ID
	private int effectId;

	public int getInameId() {
		return iname_id;
	}

	public void setInameId(int iname_id) {
		this.iname_id = iname_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getFileName() {
		return file_name;
	}

	public void setFileName(String file_name) {
		this.file_name = file_name;
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

	public int getEffectId() {
		return effectId;
	}

	public void setEffectId(int effectId) {
		this.effectId = effectId;
	}

	

}
