package com.fnx.webapp.util;

public interface WebConstants {

	/**
	 * scope: Session,Cookie.
	 */
	String USER_LANGUAGE_NAME = "language";

	int RESPONSE_CODE_SUCCESS = 0;
	int RESPONSE_CODE_FAILURE = 1;
	int RESPONSE_CODE_RESOURCE_NOT_EXIST = 2;
	int RESPONSE_CODE_ANONYMOUS = 10;
	int RESPONSE_CODE_ACCESS_DENIED = 20;

	int DEFAULT_PAGE_SIZE = 15;
}
