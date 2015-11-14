package com.fnx.webapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.util.HtmlUtils;

import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.util.ResponseMessageUtil;

public abstract class BaseController {

	protected MessageSourceAccessor messages;
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected LocaleResolver localeResolver;

	@Autowired
	@Qualifier("resourceMessageSource")
	public void setMessages(MessageSource messageSource) {
		messages = new MessageSourceAccessor(messageSource);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	public Locale getLocale() {
		return localeResolver.resolveLocale(request);
	}

	public MessageSourceAccessor getMessages() {
		return messages;
	}

	protected String retrieveSuccessMessage() {
		return retrieveMessage("success.operation");
	}

	public String retrieveMessage(String code, String... args) {
		String message = ResponseMessageUtil.getMessage(messages, code, args);
		if (args.length == 0) {
			return message;
		} else {
			return HtmlUtils.htmlEscape(message);
		}
	}

	private String getExceptionMessage(Exception e) {
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			return writer.getBuffer().toString();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	@ResponseBody
	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseModel internalServiceExceptionHandle(Exception ex) {
		logger.error("RuntimeException occered in Controller", ex);
		ResponseModel resModel = new ResponseModel();
		resModel.setResponseCodeFailure();
		if (logger.isDebugEnabled()) {
			resModel.setMsg(this.retrieveMessage("error.unexpectedException") + "<br/>" + getExceptionMessage(ex));
		} else {
			resModel.setMsg(this.retrieveMessage("error.unexpectedException"));
		}
		return resModel;
	}

	@ResponseBody
	@ExceptionHandler
	public ResponseModel baseExceptionHandle(Exception ex) {
		logger.error("Controller occur unexpected exception.", ex);
		ResponseModel resModel = new ResponseModel();
		resModel.setResponseCodeFailure();
		if (logger.isDebugEnabled()) {
			resModel.setDebugInfo(
					this.retrieveMessage("error.unexpectedException") + "<br/>" + getExceptionMessage(ex));
		}
		resModel.setMsg(this.retrieveMessage("error.unexpectedException"));
		return resModel;
	}
}
