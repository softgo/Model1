package com.bruce.gogo.utils;
/**

 * @author Liu Hongbing
 * @version 7.0
 */
import org.json.*;
import org.apache.commons.logging.*;

public class AjaxMessagesJson {

	private static final Log logger = LogFactory.getLog(AjaxMessagesJson.class);

	private JSONObject json = new JSONObject();

	public void setMessage(String codeid, String message) {
		try {
			this.json.put("codeid", codeid);
			this.json.put("message", message);
			this.json.put("text", "");
		} catch (JSONException e) {
			logger.error(e);
		}
	}

	public void setMessage(String codeid, String message, String text) {
		try {
			this.json.put("codeid", codeid);
			this.json.put("message", message);
			this.json.put("text", text);
		} catch (JSONException e) {
			logger.error(e);
		}
	}

	public String getJsonString() {
		return this.json.toString();
	}
}
