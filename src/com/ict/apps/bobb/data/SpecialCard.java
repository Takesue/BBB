package com.ict.apps.bobb.data;

public class SpecialCard extends Card {

	// 特殊効果説明
	private String effect = null;

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	@Override
	public void setValues(BeetleKit bk) {
		super.setValues(bk);
		this.setEffect(bk.getEffect());
	}

}
