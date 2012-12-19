package com.ict.apps.bobb.bobbactivity;

import com.ict.apps.bobb.base.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RuleActivity extends BaseActivity {
	
	// コメント
	private String[] commentList = {
			"これからルールの説明をします。\n" +
			"1.XXX\n" +
			"2.XXX\n" +
			"3.XXX\n" +
			"4.XXX\n" +
			"5.XXX\n",
			"・対戦の流れ",
			"・カードについて",
			"・属性について",
			"・特殊カードについて"
	};
	
	private int lineNum = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
