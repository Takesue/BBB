package com.ict.apps.bobb.battle.effect;


import java.util.ArrayList;

import com.ict.apps.bobb.battle.player.IdeaForSelectSpeCard;
import com.ict.apps.bobb.battle.player.Player;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.data.SpecialCard;

/**
 * 特殊カードの効果実装する抽象クラス
 *
 */
public abstract class EffectOfCard {

	// LP回復
	public static final int EFFECT_ID_LP_RECOVER = 1;
	// 攻撃力2倍
	public static final int EFFECT_ID_DOUBLE_ATTACK = 2;
	// 守備力２倍
	public static final int EFFECT_ID_DOUBLE_DEFENSE = 3;
	// 相手攻撃力１／２
	public static final int EFFECT_ID_HALF_ENEMY_ATTACK_ = 4;
	// 相手守備力１／２
	public static final int EFFECT_ID_HALF_ENEMY_DEFENSE = 5;
	

	/**
	 * 効果IDが一致したインスタンスを返却する。
	 * @param effectId
	 * @return
	 */
	public static EffectOfCard getEffectInstance(SpecialCard card) {
		
		EffectOfCard returnValue = null;
		int effectId = card.getEffectId();
		
		switch (effectId) {
		case EffectOfCard.EFFECT_ID_LP_RECOVER:		// ライフポイント回復
			returnValue = new EffectLifeRecovery();
			break;
		case EffectOfCard.EFFECT_ID_DOUBLE_ATTACK:		// 攻撃力2倍
			returnValue = new EffectDoubleAttack();
			break;
		case EffectOfCard.EFFECT_ID_DOUBLE_DEFENSE:		// 守備力2倍
			returnValue = new EffectDoubleDefense();
			break;
		case EffectOfCard.EFFECT_ID_HALF_ENEMY_ATTACK_:		// 相手攻撃力１／２
			returnValue = new EffectHalfEnemyAttack();
			break;
		case EffectOfCard.EFFECT_ID_HALF_ENEMY_DEFENSE:		//　相手守備力１／２
			returnValue = new EffectHalfEnemyDefense();
			break;
		default:
			// 想定外の効果ID。エラー。
			break;
		}
		
		return returnValue;
	}
	
	/**
	 * 効果を実装する。
	 */
	public abstract void execEffect(Player userInfo, Player enemyInfo);
	
	
	
	// 動画シーン用のメソッドを呼ぶ
	// 光の移動元と、移動先の定義をどこで持つべきか？
	// パネルはどう取る？
	public abstract void execEffectAnimation(Player userInfo, Player enemyInfo);
	
	

}
