package com.fnx.webapp.controller.account;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fnx.webapp.controller.BaseController;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.util.WebConstants;

@RestController
@RequestMapping("/auth")
public class AuthenticationReturnMsgController extends BaseController {

	@RequestMapping("/anonymousModel")
	public ResponseModel anonymousModel() {
		ResponseModel responseModel = new ResponseModel();
		responseModel.setCode(WebConstants.RESPONSE_CODE_ANONYMOUS);
		return responseModel;
	}

	@RequestMapping({ "/loginSuccess", "/logoutSuccess" })
	public ResponseModel success() {
		return new ResponseModel();
	}

	@RequestMapping("/loginFail")
	public ResponseModel loginFail(HttpSession session) {
		ResponseModel responseModel = new ResponseModel();
		responseModel.setCode(WebConstants.RESPONSE_CODE_FAILURE);
		Object exception = request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (exception != null && exception instanceof AuthenticationException) {
			String messageKey = ((AuthenticationException) exception).getMessage();
			responseModel.setMsg(super.retrieveMessage(messageKey));
		}
		return responseModel;
	}

	@RequestMapping("/accessDenied")
	public ResponseModel accessDenied() {
		ResponseModel responseModel = new ResponseModel();
		responseModel.setCode(WebConstants.RESPONSE_CODE_ACCESS_DENIED);
		responseModel.setMsg(super.retrieveMessage("security.error.accessDenied"));
		return responseModel;
	}
}
