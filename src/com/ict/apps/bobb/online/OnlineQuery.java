package com.ict.apps.bobb.online;

import java.util.Map;

public interface OnlineQuery {

	/**
	 * Base URL of the Demo Server
	 */
	public static final String SERVER_URL = "http://project-bobb-webapl.herokuapp.com/bobb_req";

	/**
	 * Google API project id registered to use GCM.
	 */
	public static final String SENDER_ID = "1041399877162";
	
	
	/**
	 * リクエストURLを返却
	 * @return
	 *         
	 */
	public String getServerURL();
	
	/**
	 * Postするパラメタを返却
	 * @return
	 *         Post時のパラメタを返却
	 */
	public Map<String, String> getParam();
	

}
