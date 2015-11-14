package com.fnx.webapp.util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class HttpclientUtil {

	public static HttpClient httpclient;
	public static JSONParser parser;

	static {
		httpclient = HttpClientBuilder.create().build();
		parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
	}

	public static JSONObject loadJSONArrayTopObject(String jsonString) throws ParseException {
		JSONObject result = null;
		JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
		if (!jsonArray.isEmpty()) {
			result = (JSONObject) jsonArray.get(0);
		}
		return result;
	}

	public static JSONObject parseJSONObjectString(String jsonString) throws ParseException {
		JSONObject result = (JSONObject) parser.parse(jsonString);
		return result;
	}

}
