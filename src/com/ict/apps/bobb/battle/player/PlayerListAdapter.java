package com.ict.apps.bobb.battle.player;

import android.widget.ArrayAdapter;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import com.ict.apps.bobb.bobbactivity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class PlayerListAdapter extends ArrayAdapter<Object> {

	static final int text1 = android.R.id.text1;
	static final int text2 = android.R.id.text2;
	static final int icon = android.R.id.icon;
	
	Player[] player;
	
	LayoutInflater mInflater;

	public PlayerListAdapter(Context context, 
			Object[] objects) {
		super(context, text1, objects);
		player = (Player[]) objects;
		mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_with_icon, null);
		}
		
		TextView fName = (TextView) convertView.findViewById(text1);
		TextView fDetail = (TextView) convertView.findViewById(text2);
		ImageView fIcon = (ImageView) convertView.findViewById(icon);
		fName.setText(player[position].getName());
		fDetail.setText("Lv : " + player[position].getLevel());

		if (player[position] instanceof OnlinePlayer) {
//			  fIcon.setImageResource(R.drawable.beetle1);
			 if(!"0".equals(player[position].getRequest())) {
				((TextView) convertView.findViewById(R.id.popup_request)).setText("要求");
			 }
		}
		
		return convertView;
	}
}
