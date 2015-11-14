package com.fnx.webapp.controller.account;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class SecurityLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private static final Logger logger = LogManager.getLogger(SecurityLoginUrlAuthenticationEntryPoint.class);

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public SecurityLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String redirectUrl = null;
		if (isUseForward()) {
			if (isForceHttps() && "http".equals(request.getScheme())) {
				// First redirect the current request to HTTPS.
				// When that request is received, the forward to the login page
				// will be used.
				redirectUrl = buildHttpsRedirectUrlForRequest(request);
			}
			if (redirectUrl == null) {
				String loginForm = determineUrlToUseForThisRequest(request, response, authException);
				if (logger.isDebugEnabled()) {
					logger.debug("Server side forward to: " + loginForm);
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
				dispatcher.forward(request, response);
				return;
			}
		} else {
			// redirect to login page. Use https if forceHttps true
			redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
		}
		redirectStrategy.sendRedirect(request, response, redirectUrl);
	}

	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		if ("GET".equals(request.getMethod()) && request.getRequestURI().endsWith(".jsonp")) {
			return getLoginFormUrl() + ".jsonp";
		} else {
			return getLoginFormUrl() + ".json";
		}
	}
}
