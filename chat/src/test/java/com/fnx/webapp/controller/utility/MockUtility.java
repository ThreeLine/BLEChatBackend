package com.fnx.webapp.controller.utility;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MockUtility {
	private static ObjectMapper mapper = new ObjectMapper();

	private static String convertObjectToJson(Object model) {
		String jsonParam;
		try {
			jsonParam = mapper.writeValueAsString(model);
		} catch (Exception e) {
			jsonParam = null;
		}
		return jsonParam;
	}

	public static MockHttpServletRequestBuilder populatePutBuilder(String urlTemplate, Object model,
			Object... urlVariables) {
		String jsonParam = convertObjectToJson(model);
		return MockMvcRequestBuilders.put(urlTemplate, urlVariables).contentType(MediaType.APPLICATION_JSON)
				.content(jsonParam);
	}

	public static MockHttpServletRequestBuilder populatePostBuilder(String urlTemplate, Object model,
			Object... urlVariables) {
		String jsonParam = convertObjectToJson(model);
		return MockMvcRequestBuilders.post(urlTemplate, urlVariables).contentType(MediaType.APPLICATION_JSON)
				.content(jsonParam);
	}

	public static MockHttpServletRequestBuilder populateDeleteBuilder(String urlTemplate, Object model,
			Object... urlVariables) {
		String jsonParam = convertObjectToJson(model);
		return MockMvcRequestBuilders.delete(urlTemplate, urlVariables).contentType(MediaType.APPLICATION_JSON)
				.content(jsonParam);
	}

}

