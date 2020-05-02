package com.medrep.app.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ActivityUtil {

	private static Map<String, Integer> scoreMap = new HashMap<String, Integer>();

	static
	{
		scoreMap.put("DN", 3);
		scoreMap.put("DA", 5);
		scoreMap.put("DNF",5);
		scoreMap.put("DS", 5);
		scoreMap.put("DAC", 6);
	}

	public static Integer getScore(String key)
	{
		return scoreMap.get(key);
	}

	/*public static void main(String[] args) throws Exception {
		String jsonLine  = "{\"status\": \"ok\",\"response\": {\"suggestions\": [{\"suggestion\": \"Crocin (1000 mg)\"}, {\"suggestion\": \"Crocin (15 ml)\"}, {\"suggestion\": \"Crocin Quik (500 mg)\"}]}}";

		JsonElement jelement = new JsonParser().parse(jsonLine);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    jobject = jobject.getAsJsonObject("response");
	    JsonArray jarray = jobject.getAsJsonArray("suggestions");
	    for (int i = 0; i < jarray.size(); i++) {
	    	jobject = jarray.get(i).getAsJsonObject();
		    String result = jobject.get("suggestion").toString();
		    System.out.println(result.replace("\"", "").trim());
		}
	}*/

}
