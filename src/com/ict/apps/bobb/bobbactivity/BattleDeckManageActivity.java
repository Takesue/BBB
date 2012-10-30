package com.ict.apps.bobb.bobbactivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class BattleDeckManageActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_battledeckmanage);
		initTabs();
	}

	protected void initTabs() {

		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		// 独自のタブ用レイアウト生成
		ViewGroup tabView1 = (ViewGroup) ((LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.tab_view, null);

		// タブに表示するテキストの設定
		TextView text = (TextView) tabView1.findViewById(R.id.tabText);
		text.setText("デッキ設定");
		// TabSpec生成
		intent = new Intent().setClass(this, BattleDeckForNormalTab.class);
		TabSpec tab = tabHost
				.newTabSpec("tab1")
				.setIndicator(tabView1)
				.setContent(intent);
		tabHost.addTab(tab);

		// 独自のタブ用レイアウト生成
		ViewGroup tabView2 = (ViewGroup) ((LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.tab_view, null);
		// タブに表示するテキストの設定
		TextView text2 = (TextView) tabView2.findViewById(R.id.tabText);
		text2.setText("特殊カード設定");
		
		// Tab2
		intent = new Intent().setClass(this, BattleDeckForSpecialTab.class);
		spec = tabHost
				.newTabSpec("tab2")
				.setIndicator(tabView2)
				.setContent(intent);
		tabHost.addTab(spec);

		
//		// Tab1
//		intent = new Intent().setClass(this, BattleDeckForNormalTab.class);
//		spec = tabHost
//				.newTabSpec("tab1")
//				.setIndicator("デッキ設定", res.getDrawable(R.drawable.ic_tab_normal))
//				.setContent(intent);
//		tabHost.addTab(spec);

//		// Tab2
//		intent = new Intent().setClass(this, BattleDeckForSpecialTab.class);
//		spec = tabHost
//				.newTabSpec("tab2")
//				.setIndicator("特殊カード設定", res.getDrawable(R.drawable.ic_tab_normal))
//				.setContent(intent);
//		tabHost.addTab(spec);

		// Set Default Tab - zero based index
		tabHost.setCurrentTab(0);

	}
	
}
