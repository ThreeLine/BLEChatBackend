package com.fnx.webapp.util;

public class WebappPropertiesUtil {

	private static String uploadPath;
	private static String uploadContext;

	public static String getUploadPath() {
		if (uploadPath == null) {
			throw new IllegalArgumentException("property is not initialized");
		}
		return uploadPath;
	}

	public static void setUploadPath(String uploadPath) {
		WebappPropertiesUtil.uploadPath = uploadPath;
	}

	public static String getUploadContext() {
		if (uploadContext == null) {
			throw new IllegalArgumentException("property is not initialized");
		}
		return uploadContext;
	}

	public static void setUploadContext(String uploadContext) {
		WebappPropertiesUtil.uploadContext = uploadContext;
	}
}
