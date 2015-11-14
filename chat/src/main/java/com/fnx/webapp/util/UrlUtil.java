package com.fnx.webapp.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minidev.json.JSONObject;

public class UrlUtil {

	private final static Logger logger = LogManager.getLogger(UrlUtil.class);

	/**
	 * if url is not valid, will return null.
	 * 
	 * @param url
	 * @return
	 */
	public static String loadDomainName(String url) {
		String result = null;
		try {
			URI uri = new URI(url);
			result = uri.getHost();
		} catch (URISyntaxException e) {
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	public static String generateSinaShortUrl(String url) {
		String result = null;
		try {
			String requestUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source=1681459862&url_long=" + url;
			HttpGet request = new HttpGet(requestUrl);
			request.addHeader("accept", "application/json");
			HttpResponse response;
			response = HttpclientUtil.httpclient.execute(request);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			JSONObject object = HttpclientUtil.loadJSONArrayTopObject(jsonStr);
			result = object.getAsString("url_short");
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		} catch (net.minidev.json.parser.ParseException e) {
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	public static String generateBaiduShortUrl(String url) {
		String result = "";
		try {
			// 配制ignore cookies将解决以下这个WARNING
			// WARNING: Cookie rejected [BAIDUID
			RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
			HttpPost httpost = new HttpPost("http://dwz.cn/create.php");
			httpost.setConfig(requestConfig);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("url", url));
			httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = HttpclientUtil.httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			JSONObject object = HttpclientUtil.parseJSONObjectString(jsonStr);
			result = object.getAsString("tinyurl");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getLocalizedMessage());
		} catch (ClientProtocolException e) {
			logger.error(e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		} catch (net.minidev.json.parser.ParseException e) {
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

}
