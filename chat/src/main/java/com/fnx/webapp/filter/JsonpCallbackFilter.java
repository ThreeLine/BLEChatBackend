package com.fnx.webapp.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonpCallbackFilter implements Filter {
	private static final Logger log = LogManager.getLogger(JsonpCallbackFilter.class);
	private String param;
	private String defaultValue;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if ("GET".equals(httpRequest.getMethod())) {
			String callbackPrefix = request.getParameter(param);
			if (StringUtils.isBlank(callbackPrefix)) {
				callbackPrefix = defaultValue;
			}
			if (log.isDebugEnabled()) {
				log.debug("Wrapping response with JSONP callback '" + callbackPrefix + "'");
			}
			ServletOutputStream out = httpResponse.getOutputStream();
			GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);
			chain.doFilter(request, wrapper);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.write((callbackPrefix + "(").getBytes());
			outputStream.write(wrapper.getData());
			outputStream.write(");".getBytes());
			byte jsonpResponse[] = outputStream.toByteArray();
			out.write(jsonpResponse);
			out.close();
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {

	}

}
