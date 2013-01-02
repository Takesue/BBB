package com.ict.apps.bobb.online;

import android.content.Context;

public interface OnlineResponseListener {
	
	public void response(Context context, OnlineQuery query, Integer result);

}
