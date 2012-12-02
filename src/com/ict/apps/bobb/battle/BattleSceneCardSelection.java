package com.ict.apps.bobb.battle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.ict.apps.bobb.battle.player.MyPlayer;
import com.ict.apps.bobb.battle.player.Player;
import com.ict.apps.bobb.bobbactivity.BattleActivity;
import com.ict.apps.bobb.bobbactivity.BattleCardView;
import com.ict.apps.bobb.bobbactivity.BattleLayout;
import com.ict.apps.bobb.bobbactivity.R;
import com.ict.apps.bobb.data.BeetleCard;
import com.ict.apps.bobb.data.CardAttribute;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BattleSceneCardSelection implements BattleScene {

	// 対戦画面アクティビティ
	private BattleActivity activity = null;

	// カード詳細表示部品(carddetail.xml)
	private View myViewCardDerail = null;

	// 合計攻撃力
	private int totalAttack = 0;
	
	// 合計守備力
	private int totalDefense = 0;

	// 3枚カードを選択済みフラグ
	public boolean threeCardselected = false;
	
	/**
	 * コンストラクタ
	 * @param activity 対戦画面のアクティビティ
	 */
	public BattleSceneCardSelection(BattleActivity activity) {
		this.activity = activity;
	}

	@Override
	public void init() {
		
		// 手札を表示する （自分）
		this.displayCards(0);
		// 手札を表示する （相手）
		this.displayCards(1);
		
		// 合計値表示
		this.viewTotal(5, 180);
		
		// 特殊カード選択spinner表示
		this.viewSpinner();
		
		// 制限時間タイマー開始
		this.startLimitTimeCountDown(30);
	}
	/**
	 * 特殊カード選択spinner表示
	 */
//    private ArrayList<Integer>  spinnerId = new ArrayList<Integer>();
	private int spinnerId = 0;
	public void viewSpinner() {
		
		Spinner spinner = new Spinner(this.activity);

		ArrayList<String>  spnStrings = new ArrayList<String>();
	    ArrayList<Integer> spnId = new ArrayList<Integer>();
/*		setSpinnerDate(i)の「i」の部分にはeffectIdを入れる予定
 *		effectIdごとで説明文をspinnerにセットできれば・・・
 *		ということでsetSpinnerDate(int id)メソッドを追加
 *		使用済みなどの判断を追加して使用済みをspinnerにセット
 *		しないようにできればいいと思うのだが・・・
 *		指定したIDを使用したいので、よく考えよう。
 */
		spnStrings.add("特殊Ｃ使用しない");
		spnId.add(0);
	    
	    final ArrayList<BattleCardView> spCards =  this.activity.myPlayer.specialInfo.getHoldCard();
	    
		int length = spCards.size();
		for (int i = 0; i < length; i++) {
			spnStrings.add(spCards.get(i).getEffect());
			spnId.add(spCards.get(i).getEffectId());
		}
	    final ArrayList<Integer> sendSpnId = new ArrayList<Integer>(spnId);
		// Densityの値を取得
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
				(int)(300 * tmpDensity),(int)(60 * tmpDensity));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(110 * tmpDensity),
				(int)(180 * tmpDensity),0,0);
		this.activity.baseLayout.addView(spinner,cartParams);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.activity,
		android.R.layout.simple_spinner_item, spnStrings);
		//スピナーの背景に画像を張り付ける
		spinner.setBackgroundResource(R.drawable.spn_view);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		//スピナーのクリックイベントを取得する
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			//Itemが選択された時
			public void onItemSelected(AdapterView parent,
				View view, int position, long id) {
				//parentのspinnerを指定
				Spinner spinner = (Spinner) parent;
				//選択されたitemを取得
				String item = (String) spinner.getSelectedItem();

				Long itemId = (Long) spinner.getSelectedItemId();
				spinnerId = sendSpnId.get(Integer.parseInt(itemId.toString()));
				int length = spCards.size();
				for (int i = 0; i < length; i++) {
					//選択された効果が０であれば他の状態を０に戻す
					if(itemId == 0){
						activity.myPlayer.specialInfo.resetCard(spCards.get(i));
					//選択された効果のものは状態を１にする
					}else if(itemId == i + 1){
						activity.myPlayer.specialInfo.selectCard(spCards.get(i));
					//選択された効果の以外ものは状態を０に戻す
					}else{
						activity.myPlayer.specialInfo.resetCard(spCards.get(i));
					}
				}
/*				//Toast表示
				Toast.makeText(MainActivity.this,
					String.format("%sが選択されました。", item),
					Toast.LENGTH_SHORT).show();
*/
			}
			//何も選択されなかったとき
			public void onNothingSelected(AdapterView parent) {
/*				Toast.makeText(MainActivity.this,
						"何も選択されませんでした", Toast.LENGTH_SHORT).show();
*/
			}
		});
	}
	
	/**
	 * 再表示
	 */
	public void reviw(int type) {
		
		ArrayList<BattleCardView> viewCards = null;
		
		// 自分のカードを全部取得
		Player info = null;
		if (type == 0) {
			info = this.activity.myPlayer;
		}
		else {
			info = this.activity.enemyPlayer;
		}
		viewCards = info.cardInfo.getAllCards();
		

		int length = viewCards.size();
		for (int i = 0; i < length; i++) {
			
			// 手札の場合、のみ表示する
			if ((info.cardInfo.getStatus(viewCards.get(i)) == 1)
					|| (info.cardInfo.getStatus(viewCards.get(i)) == 2)){
				
				// 戦闘ベース部品にcard追加する
				this.activity.baseLayout.addView(viewCards.get(i));
			}
		}
		
	}
	
	/**
	 * カードを表示する
	 * @param type
	 */
	public void displayCards(int type) {
		
		ArrayList<BattleCardView> viewCards = null;
		
		// 自分のカードを全部取得
		Player info = null;
		if (type == 0) {
			info = this.activity.myPlayer;
		}
		else {
			info = this.activity.enemyPlayer;
		}
		viewCards = info.cardInfo.getAllCards();
		
		// Densityの値を取得
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;

		int length = viewCards.size();
		for (int i = 0; i < length; i++) {
			
			// 手札の場合、のみ表示する
			if ((info.cardInfo.getStatus(viewCards.get(i)) == 1)
					|| (info.cardInfo.getStatus(viewCards.get(i)) == 2)){
				BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
						(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.card_width)),
						(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.card_height)));
				cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				cartParams.setMargins((int)(viewCards.get(i).getStartPosLeft() * tmpDensity),
						(int)(viewCards.get(i).getStartPosTop() * tmpDensity),
						0,
						0);
				
				// 戦闘ベース部品にcard追加する
				this.activity.baseLayout.addView(viewCards.get(i), cartParams);
			}
			
		}
		
	}


	/**
	 * 3枚拡大表示Viewから、対応する通常サイズカードViewを見つける
	 * @param bigCard
	 * @return
	 */
	private BattleCardView searchCardFromBigCard (BattleCardView bigCard) {
		
		BattleCardView view = null;
		BeetleCard info = bigCard.getCardInfo();
		ArrayList<BattleCardView> cards = this.activity.myPlayer.cardInfo.getSelectedCard();
		for(BattleCardView selectedCard : cards) {
			if (info.equals(selectedCard.getCardInfo())) {
				// 一致したら入れ替える
				view = selectedCard;
				view.setUpFlag(false);
				break;
			}
		}
		return view;

	}
	
	@Override
	public void moveCard(BattleCardView view, int action) {

		if (this.activity.myPlayer.cardInfo.getStatus(view) == null) {
			
			for ( BattleCardView card : this.bigCards) {
				if ((view.equals(card)) && (action == 1) && (this.threeCardselected == true)){
					// カードと押されたカードが同じで、且つ下げイベントで且つ、3枚選択されている状態の場合
					
					// 下げるビューを見つける。
					
					view = this.searchCardFromBigCard(card);
					if (view != null) {
						break;
					}
				}
			}
			if (this.activity.myPlayer.cardInfo.getStatus(view) == null) {
				// 入れ替えてもなおNULLなら何もしない
				return;
			}
		}
		
		int status = this.activity.myPlayer.cardInfo.getStatus((BattleCardView) view);
		// カードのステータスが　手札、手札から選択の場合、上げ下げ可能
		// Densityの値を取得
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;

		if (status == 1) {
			// カードを押さえてカードより上の座標ずらしたら、カードを上にずらす。
			if ((action == 0) && (this.threeCardselected == false)){
				BattleLayout.LayoutParams params = (BattleLayout.LayoutParams)view.getLayoutParams();
				params.setMargins((int)((view).getStartPosLeft() * tmpDensity), (int)(((view).getStartPosTop() - 20)* tmpDensity), 0, 0);
				view.setLayoutParams(params);
				
				// 選択
				this.activity.myPlayer.cardInfo.selectCard(view);
				
				// 選択状況を解析
				this.analyzeSelectCards();
				
				// 効果音設定
				this.activity.playEffect(R.raw.card_set);

			}
		}
		else if (status == 2) {
			// カードを押さえてカードより下の座標にずらしたらカードを元の位置に戻す
			if (action == 1) {
				
				if (this.threeCardselected == true) {
					
					this.rollbackBefoeThreeSelect();

				}
				
				BattleLayout.LayoutParams params = (BattleLayout.LayoutParams)view.getLayoutParams();
				params.setMargins((int)((view).getStartPosLeft()*tmpDensity), (int)(((view).getStartPosTop())* tmpDensity), 0, 0);
				view.setLayoutParams(params);

				// 選択解除
				this.activity.myPlayer.cardInfo.dealCard(view);

				// 選択状況を解析
				this.analyzeSelectCards();

				// 効果音設定
				this.activity.playEffect(R.raw.card_set);
			}
		}
		
	}

	/**
	 * 3枚選ぶ前の状態に戻る
	 */
	private void rollbackBefoeThreeSelect() {
		this.threeCardselected = false;
		this.finish();
		this.reviw(0);
		this.reviw(1);
		this.viewTotal(5, 180);
		this.viewSpinner();
	}
	
	@Override
	public void finish() {
		// 表示しているビューを全て削除する
		this.activity.baseLayout.removeAllViews();

	}

	@Override
	public void onLongClickCard(BattleCardView view) {
		
		// 手札の時だけ表示する。
		if (this.activity.myPlayer.cardInfo.getStatus(view) == 1) {
			// 表示する
			this.viewDetailCards(view, 20, 10);
		}
	}
	
	@Override
	public void actionUpCard(BattleCardView view) {
		this.invisibleCardDetail();
	}

	
	/**
	 * 3枚カード選択時にフラグが立つ
	 * @return
	 */
	public boolean isThreeCardselected() {
		return threeCardselected;
	}

	/**
	 * カード詳細を画面に表示する
	 * @param left
	 * @param top
	 */
	public void viewDetailCards(View view, int left, int top) {
		
		BattleCardView card = (BattleCardView)view;

		// Densityの値を取得
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		
		// CARD用View取得
		this.myViewCardDerail = ((LayoutInflater) this.activity.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.card_detailview, null);
		
		int posLeft = (int)(this.activity.baseLayout.getWidth()/2 - 266*tmpDensity/2);
		
		BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
				(int)(266*tmpDensity),
				(int)(400*tmpDensity));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(posLeft), (int)(top*tmpDensity), 0, 0);
		
		// 名前
		((TextView)this.myViewCardDerail.findViewById(R.id.carddetail_name)).setText(
				card.getCardInfo().getName());
		
		// 画像設定
		((ImageView)this.myViewCardDerail.findViewById(R.id.carddetail_icon)).setImageResource(card.getCardInfo().getImageResourceId(this.activity));
		
		// 属性設定
		CardAttribute attr = card.getCardInfo().getAttribute();
		if (attr == CardAttribute.FIRE) {
			((ImageView)this.myViewCardDerail.findViewById(R.id.carddetail_attrribute)).setImageResource(R.drawable.fire);
		}
		else if (attr == CardAttribute.WATER) {
			((ImageView)this.myViewCardDerail.findViewById(R.id.carddetail_attrribute)).setImageResource(R.drawable.water);
		}
		else if (attr == CardAttribute.WIND) {
			((ImageView)this.myViewCardDerail.findViewById(R.id.carddetail_attrribute)).setImageResource(R.drawable.wind);
		}

		int type = card.getCardInfo().getType();
		if (type == 3) {
			
			// 背景設定
			((RelativeLayout)this.myViewCardDerail.findViewById(R.id.carddetail_bg)).setBackgroundResource(R.drawable.card_red);
			
			// 攻、守
			// 表示文字設定
			((TextView)this.myViewCardDerail.findViewById(R.id.carddetail_atk)).setText(
					"攻撃：" + Integer.toString(card.getCardInfo().getAttack()));
		}
		if (type == 4) {
			
			// 背景設定
			((RelativeLayout)this.myViewCardDerail.findViewById(R.id.carddetail_bg)).setBackgroundResource(R.drawable.card_blue);

			// 攻、守
			// 表示文字設定
			((TextView)this.myViewCardDerail.findViewById(R.id.carddetail_def)).setText(
					"守備：" + Integer.toString(card.getCardInfo().getDefense()));
		}

		// 説明
		((TextView)this.myViewCardDerail.findViewById(R.id.carddetail_intoro)).setText(
				"説明：" + card.getCardInfo().getIntroduction());
		
		
		
		// 戦闘ベース部品にカード詳細を追加する
		this.activity.baseLayout.addView(this.myViewCardDerail, cartParams);
		
	}
	
	/**
	 * カード詳細を消す
	 */
	private void invisibleCardDetail() {
		
		if (this.activity.baseLayout == null) {
			return;
		}
		
		if (this.myViewCardDerail != null) {
			// 親グループからカード詳細を外す
			this.activity.baseLayout.removeView(this.myViewCardDerail);
		}
		// 画面再描画を要求
		this.activity.baseLayout.invalidate();
	}

	/**
	 * 合計表示を画面に表示する
	 * @param left
	 * @param top
	 */
	public void viewTotal(int left, int top) {
		
		// Densityの値を取得
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		

		// CARD用View取得
		LinearLayout totalView = (LinearLayout) ((LayoutInflater) this.activity.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.battle_totalpoint, null);
		
		BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
				(int)(100 * tmpDensity),
				(int)(120 * tmpDensity));
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cartParams.setMargins((int)(left*tmpDensity), (int)(top*tmpDensity), 0, 0);
		
		// 攻撃力合計
		((TextView)totalView.findViewById(R.id.battle_total_attack)).setText("0");
		
		// 守備力合計
		((TextView)totalView.findViewById(R.id.battle_total_defense)).setText("0");
		
		
		// 戦闘ベース部品にカード詳細を追加する
		this.activity.baseLayout.addView(totalView, cartParams);
		
	}

	/**
	 * 選択されているカード情報を解析します。
	 * 選択カード数、選択したカードの合計値
	 */
	private void analyzeSelectCards() {
		
		ArrayList<BattleCardView> cards = this.activity.myPlayer.cardInfo.getSelectedCard();
		
		// 攻撃力と守備力の合計値を算出して表示する
		this.calcAndViewTotal(cards);
		
		// 3枚選択された場合
		if (cards.size() == 3) {
			// ボタンUPに蓋をする。
			this.threeCardselected = true;
			
			// 3枚をデカくする？
			this.viewSelectedBigCardDisp();
			
		}
	}
	
	/**
	 * 攻撃力と守備力の合計値を算出して表示する
	 */
	private void calcAndViewTotal(ArrayList<BattleCardView> cards) {
		
		this.totalAttack = 0;
		this.totalDefense = 0;

		// 合計値加算
		for (BattleCardView card : cards) {
			int type = card.getCardInfo().getType();
			if (type == 3) {
				// 攻撃力加算
				this.totalAttack += card.getCardInfo().getAttack();
			}
			else if (type == 4) {
				// 守備力加算
				this.totalDefense += card.getCardInfo().getDefense();
			}
		}
		
		// 3枚選択された場合属性一致であれば、合計値を変更する
		if (cards.size() == 3) {
			CardAttribute myAtt = this.activity.myPlayer.cardInfo.getAttribute(cards); 
			if(myAtt != null){
				int[] atts = this.activity.myPlayer.cardInfo.judgeAttribute(myAtt, null, this.totalAttack, this.totalDefense);
				this.totalAttack = atts[0];
				this.totalDefense = atts[1];
			}
		}
		// 特殊カード使用時、必要であれば値を変更する
		int[] special = this.activity.myPlayer.specialInfo.judgeSpecial(this.spinnerId, 0, this.totalAttack, this.totalDefense, 0, 0);
		this.totalAttack = special[0];
		this.totalDefense = special[1];
		this.activity.myPlayer.specialInfo.spinnerId = this.spinnerId;
		
		// 攻撃力合計
		((TextView)this.activity.findViewById(R.id.battle_total_attack)).setText(Integer.toString(this.totalAttack));
		
		// 守備力合計
		((TextView)this.activity.findViewById(R.id.battle_total_defense)).setText(Integer.toString(this.totalDefense));
		
	}
	
	
	// ３つ選択して大きくなった状態のカードを生成して保持する。
	ArrayList<BattleCardView> bigCards = new ArrayList<BattleCardView>();
	
	/**
	 * 選択カードの確認拡大表示
	 */
	private void viewSelectedBigCardDisp() {
		// 表示を一旦クリア
		this.finish();
		this.bigCards.clear();
		
		// 選択した3つを中央に表示
		// 多少拡大する
		ArrayList<BattleCardView> cards = this.activity.myPlayer.cardInfo.getSelectedCard();
		
		int posX = 10;
		int posY = 160;
		float tmpDensity = this.activity.getResources().getDisplayMetrics().density;
		int myCardMarginX = (int) ((new Float(this.activity.baseLayout.getWidth())/tmpDensity - (new Float(posX)*2))/3);
		
		int length = cards.size();
		for (int i = 0; i < length; i++) {
			// Densityの値を取得
			
			BattleCardView viewCard = (BattleCardView)((LayoutInflater) this.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.my_cards_big, null);
			
			viewCard.setControlActivity(this.activity);

			BattleLayout.LayoutParams cartParams = new BattleLayout.LayoutParams(
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.big_card_width)),
					(int)(this.activity.getResources().getDimensionPixelSize(R.dimen.big_card_height)));
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			cartParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			cartParams.setMargins((int)((posX + (myCardMarginX * i)) *tmpDensity), (int)(posY*tmpDensity), 0, 0);
			viewCard.setBeetleCard(cards.get(i).getCardInfo());
			viewCard.setUpFlag(true);
			viewCard.flippedCardFace();
			
			// 参照を保持する
			this.bigCards.add(viewCard);
			
			// 戦闘ベース部品にcard追加する
			this.activity.baseLayout.addView(viewCard, 0, cartParams);
		}

		// 合計パネルを右上に表示
		this.viewTotal(200, 10);
		this.calcAndViewTotal(cards);
		
		this.setDealButton();
	}
	
	/**
	 * BATTLEボタンを画面に設定する
	 * @param type
	 */
	public void setDealButton() {

		Button button = new Button(this.activity);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectCardFinished();
			}
		});
		
		
		BattleLayout.LayoutParams params = new BattleLayout.LayoutParams(
				BattleLayout.LayoutParams.MATCH_PARENT,
				this.activity.baseLayout.getHeight()*1/3);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.setMargins((int)(0), (int)(this.activity.baseLayout.getHeight()*2/3), 0, 0);
		
		button.setLayoutParams(params);
		button.setBackgroundResource(R.drawable.battle_battle_button);

		// 戦闘ベース部品にcard追加する
		this.activity.baseLayout.addView(button, params);

	}
	
	/**
	 * カード選択終了した場合に実行する。
	 * カード選択終了処理及び、対戦管理クラスへ終了を通知
	 * 本来はfinish()メソッドで実施すべきだが、本クラス内では、カード選択確定表示の際の
	 * 画面クリアとしてfinishを使用しているため、実質的に本メソッドがこのクラスの終了処理メソッドとなる。
	 */
	private void selectCardFinished() {
		
		// 戦闘シーンへ移動するため、値を戻す
		threeCardselected = false;
		
		// 制限時間タイマー停止
		stopLimitTimeCountDown();
		
		// 戦闘シーンへ移動
		activity.bm.selectCardFinished();

	}
	
	// 
	private Timer mTimer = null;
	private int counter = -1;
	
	/**
	 * タイマー開始
	 * @param count
	 */
	public void startLimitTimeCountDown(int count) {
		
		// タイマー実行間隔　m秒
		final int duration = 1000;	
		final android.os.Handler handler = new android.os.Handler();
		this.mTimer = new Timer();
		
		// カウントダウン時間を設定
		this.counter = count;

		this.mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						// 残り秒数表示
						((TextView)activity.findViewById(R.id.battle_timelimit)).setText("制限時間 :" + counter + "秒");
						if (counter == 0) {
							
							// 制限時間経過時の処理
							// 強制的にカード選択させる。
							((MyPlayer)activity.myPlayer).forcedCardSelection();
							
							// カード選択シーン終了
							selectCardFinished();
						}
						else {
							// カウントダウン
							counter--;
						}
					}
				});
			}
		}, 0, duration);
	}
	
	/**
	 * タイマー停止
	 * @param count
	 */
	public void stopLimitTimeCountDown() {
		this.mTimer.cancel();
	}



}
