package com.ict.apps.bobb.data;

public class SpecialCard extends Card {

	// 特殊効果説明
	private String effect = null;
	// 特殊効果ID
	private int effectId = 0;
	

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

	@Override
	public void setValues(BeetleKit bk) {
		super.setValues(bk);
		this.setEffect(bk.getEffect());
		this.setEffectId(bk.getEffectId());
	}

}
