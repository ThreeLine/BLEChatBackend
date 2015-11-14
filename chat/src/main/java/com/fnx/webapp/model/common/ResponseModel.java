package com.fnx.webapp.model.common;

import com.fnx.webapp.util.WebConstants;

public class ResponseModel {

	private int code = WebConstants.RESPONSE_CODE_SUCCESS;
	private String msg = "";
	private Object data;
	private String debugInfo;

	public ResponseModel() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setResponseCodeFailure() {
		this.code = WebConstants.RESPONSE_CODE_FAILURE;
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
