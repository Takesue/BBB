package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RuleActivity extends BaseActivity {
	
	// コメント
//	private String[] commentList = {
//			"これからルールの説明をします。\n" +
//			"1.XXX\n" +
//			"2.XXX\n" +
//			"3.XXX\n" +
//			"4.XXX\n" +
//			"5.XXX\n",
//			"・対戦の流れ",
//			"・カードについて",
//			"・属性について",
//			"・特殊カードについて"
//	};
	
	private String[] commentList = {
			"０．はじめに\n" +
			"　二人のプレイヤで行う対戦型カードゲームです。\n" +
			"　プレイヤは各自がデッキを用意して対戦します。\n" +
			"　デッキは３０枚の攻守カード（攻撃カード・守備カード）と３枚の特殊カードで構成されます。\n" +
			"　\n" +
			"　ゲームは以下の①～③を１ターンとし、\n" +
			"勝敗が決するまで繰り返します。\n" +
			"　①ドローフェイズ\n" +
			"　②カード選択フェイズ\n" +
			"　③バトルフェイズ\n" +
			"",		//　次ページ
			"１．ドローフェイズ\n" +
			"　まずゲーム開始時にデッキの内、攻守カード３０枚を山札にセットします。\n" +
			"　初回ターンは山札からカードを５枚ドロー（引き）、手札にします。\n" +
			"　２回目以降のターンは３枚ドローして手札にします。\n" +
			"",		//　次ページ
			"２．カード選択フェイズ\n" +
			"　５枚の手札の中から、使用する攻守カードを3枚選択します。\n" +
			"　この時、使用可能な特殊カードがあれば、さらに特殊カードを1枚選択できます。\n" +
			"　使用するカードが決まったらバトルフェイズの開始です。\n" +
			"",		//　次ページ
			"３．バトルフェイズ\n" +
			"　プレイヤはお互いに確定した３枚のカードをオープン（みせあい）します。\n" +
			"　※特殊カードを使用している場合は特殊カードもオープンします。\n" +
			"　プレイヤは予めＬＰ（ライフポイント）が設定されており\n" +
			"　バトルフェイズではダメージ値を算出してＬＰからダメージ値を削減します。\n" +
			"　バトルフェイズではオープンしたカードに基づいて以下の算出式でダメージ値の計算を行います。\n" +
			"",		//　次ページ
			"　①プレイヤの攻撃力を算出する。\n" +
			"　　プレイヤの攻撃力 ＝ ３枚のカードの内、攻撃カードの合計値\n" +
			"\n" +
			"　②プレイヤの守備力を算出する。\n" +
			"　　プレイヤの守備力 ＝ ３枚のカードの内、守備カードの合計値\n" +
			"\n" +
			"　※特殊カード使用時は、特殊効果の内容に応じたステータス計算を加味します。\n" +
			"\n" +
			"　③ダメージ値を算出する。\n" +
			"　　・自プレイヤのダメージ値 ＝ 相手プレイヤの攻撃力 - 自プレイヤの守備力\n" +
			"　　・相手プレイヤのダメージ値 ＝ 自プレイヤの攻撃力 - 相手プレイヤの守備力\n" +
			"\n" +
			"　※使用済のカードは今ゲーム中は再使用できません。\n" +
			"",		//　次ページ
			"４．勝敗条件\n" +
			"　バトルフェイズ終了時点の両プレイヤのＬＰの状態によって勝敗が決します。\n" +
			"　相手プレイヤのＬＰが０になるか、山札が無くなった時点でＬＰが相手プレイヤより多い場合、勝利となります。\n" +
			"",		//　次ページ
			"　自プレイヤのＬＰが０になるか、山札が無くなった時点でＬＰが相手プレイヤより少ない場合、負けとなります。\n" +
			"　山札が無くなった時点でＬＰが同じ場合や両プレイヤのＬＰが同じターンで０になった場合、引き分けとなります。\n" +
			"\n" +
			"",		//　次ページ
			"補足.属性について\n" +
			"　カードには火・水・風の3つの属性があります。\n" +
			"　カードをオープンする時、攻守カードの属性が３つ揃えになると攻・守のステータスが上がります。\n" +
			"　また、属性には優劣関係があり両者が属性を揃えた場合、関係性により攻・守のステータスが上下します。\n" +
			"　関係性は以下の通りになります。\n" +
			" 　・火は風に強い\n" +
			" 　・風は水に強い\n" +
			" 　・水は火に強い\n" +
			"",		//　次ページ
			"＜対戦の進め方＞\n" +
			"１．対戦相手が決まると対戦開始となります。\n" +
			"　　対戦が開始すると、初回ターンのドローフェイズになります。\n" +
			"２．ドローは、画面上の「PUSH」を押すか、２秒経過すると自動で行われます。\n" +
			"３．ドローが完了するとカード選択フェイズになります。\n" +
			"",		//　次ページ
			"４．手札のカードに触れて上にスライドさせることで、使用するカードを選択します。\n" +
			"　　※選択済みのカードを下にスライドさせると選択解除になります。\n" +
			"　　※カード選択フェイズでは、未選択のカードを長押しすることでカードの詳細を確認することができます。\n" +
			"",		//　次ページ
			"５．攻守カードを３枚選択すると、最終確認画面が表示されます。\n" +
			"　　※最終確認画面で選択されているカードを変更したい場合は画面に表示されている攻守カードを下にスライドさせてください。\n" +
			"　　　前の画面に戻ります。\n" +
			"　　※最終確認画面では特殊カードを変更できないので、攻守カードを下にスライドさせて、一旦、前の画面に戻ってから変更してください。\n" +
			"　　※使用カードの選択には３０秒の制限時間が設けられており、３０秒を過ぎると任意の攻守カードが選択されてバトルフェイズに移行します。\n" +
			"",		//　次ページ
			"６．最終確認画面で「Push BattleStart」を押すとバトルフェイズになります。\n" +
			"　　ダメージ計算とアニメーションが自動で行われて今のターンが終了し、次のターンに移行します。"
	};

	
	
	private int lineNum = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_rule);

		TextView fukidasi = (TextView) this.findViewById(R.id.rule_fukidasi);
		fukidasi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nextComment(v);
			}
		});
		this.nextComment(fukidasi);
	}
	
	/**
	 * 次のコメントを表示
	 * @param v
	 */
	public void nextComment(View v) {
		
		// 最終行に到達したら、最初の行に戻る
		if (this.lineNum == this.commentList.length) {
			this.lineNum = 0;
		}
		
		// 表示行数がコメント総行数より小さい間はコメント実施
		((TextView) v).setText(this.commentList[lineNum++]);
		
	}	
	
	
    public void returnOnClick(View v){
    	
//		Intent intent = new Intent(RuleActivity.this, StartActivity.class);
//		startActivity(intent);
    	this.finish();
		
    }
    
	

}
