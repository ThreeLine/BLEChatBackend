package com.fnx.webapp.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class BaseModel implements Serializable {

	/**
	 * 不能包含分号.
	 */
	public static final String REGEXP_CANNOT_CONTAIN_SEMICOLON = "^(?!.*;).*$";
	/**
	 * can not contain $, {, }
	 */
	public static final String PROPERTY_DISPLAY_NAME_REGEXP_CANNOT_CONTAIN_SPECIAL_CHARACTER = "^(?!.*[\\$\\{\\}]).*$";

	/**
	 * only can input english letters, number and space.
	 */
	public static final String STRING_CANNOT_CONTAIN_SPECIAL_CHARACTER = "^(\\d|\\w|\\s)*$";

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
