package com.ict.apps.bobb.data;

/**
 * 虫カードクラス
 *
 */
public class BeetleCard extends Card {
	
	// 攻撃力
	private int attack = 0;
	
	// 防御力
	private int defence = 0;
	
	// 属性
	private CardAttribute attribute;
	
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
	
	/**
	 * カード種別：攻撃
	 */
	public void setTypeAttack() {
		this.setType(3);
	}

	/**
	 * カード種別：防御
	 */
	public void setTypeDefence() {
		this.setType(4);
	}

	public CardAttribute getAttribute() {
		return attribute;
	}
	
	public void setAttribute(CardAttribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public void setValues(BeetleKit bk) {
		super.setValues(bk);
		
		this.setAttack(bk.getAttack());
		this.setDefence(bk.getDefence());

	}

}
