package com.fnx.webapp.util;

import java.util.Locale;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 
 * @author root
 * 
 */
public class ResponseMessageUtil {

	/**
	 * 该方法适用于获取普通的国际化信息
	 * 
	 * @param msgSource
	 * @param key
	 * @param args
	 * @param locale
	 * @return
	 */
	public static final String getMessage(MessageSourceAccessor msgSource, String key, String... args) {
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				args[i] = msgSource.getMessage(args[i]);
			}
		}
		return msgSource.getMessage(key, args);
	}

	/**
	 * 该方法适用于获取普通的国际化信息
	 * 
	 * @param msgSource
	 * @param locale
	 * @param key
	 * @param args
	 * @return
	 */
	public static final String getMessage(MessageSourceAccessor msgSource, Locale locale, String key, String... args) {
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				args[i] = msgSource.getMessage(args[i], locale);
			}
		}
		return msgSource.getMessage(key, args, locale);
	}

	/**
	 * 该方法适用于获取普通的国际化信息
	 * 
	 * @param msgSource
	 * @param key
	 * @param locale
	 * @return
	 */
	public static final String getMessage(MessageSourceAccessor msgSource, Locale locale, String key) {
		return getMessage(msgSource, locale, key, new String[] {});
	}

	/**
	 * 该方法只适用于获取通过Hibernate Validator进行数据校验的国际化信息
	 * 
	 * @param result
	 * @param msgSource
	 * @param locale
	 * @return
	 */
	public static final String getMessage(BindingResult result, MessageSourceAccessor msgSource, Locale locale) {

		Assert.notNull(result);

		// 只返回一个错误
		ObjectError objError = result.getAllErrors().get(0);

		convertArgumentsToI18n(objError, msgSource, locale);

		String errMsg = "";
		for (String code : objError.getCodes()) {
			errMsg = msgSource.getMessage(code, objError.getArguments(), locale);
			if (isI18nContent(code, errMsg)) {
				return errMsg;
			}
		}

		return msgSource.getMessage(objError.getDefaultMessage());
	}

	private static void convertArgumentsToI18n(ObjectError objError, MessageSourceAccessor msgSource, Locale locale) {
		Object[] args = objError.getArguments();
		if (args != null && args.length > 0) {
			if (!(objError instanceof FieldError)) {
				String prefix = "";
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof DefaultMessageSourceResolvable) {
						DefaultMessageSourceResolvable msr = (DefaultMessageSourceResolvable) args[i];
						prefix = getCode(msr);
					} else {
						if (i > 0) {
							args[i - 1] = msgSource.getMessage(prefix + args[i], locale);
						}
					}
				}
			} else {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof DefaultMessageSourceResolvable) {
						DefaultMessageSourceResolvable msr = (DefaultMessageSourceResolvable) args[i];
						String code = getCode(msr);
						args[i] = msgSource.getMessage(code, locale);
					}
				}
			}
		}
	}

	private static final String getCode(DefaultMessageSourceResolvable msr) {
		String[] codes = msr.getCodes();
		return codes != null && codes.length > 0 ? codes[0] : null;
	}

	private static final boolean isI18nContent(String code, String content) {
		return !java.util.Objects.equals(code, content);
	}
}
