package com.fnx.webapp.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

import com.fnx.webapp.controller.BaseController;
import com.fnx.webapp.model.BaseModel;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.util.ResponseMessageUtil;

/**
 * Controller validation 拦截
 * 
 * @author iverson.he
 * 
 */
@Aspect
@Configuration
public class ValidationInterceptor {
	private static final Logger logger = LogManager.getLogger(ValidationInterceptor.class);

	/**
	 * Controller必须继承BaseController,model和BindingResult必须成对出现且在最前面
	 * 
	 * @param pjp
	 * @param baseController
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@Around("this(baseController)&&args(baseModel,result,..)")
	public Object doReturnMsg(ProceedingJoinPoint pjp, BaseController baseController, BaseModel baseModel, BindingResult result) throws Throwable {
		if (result.hasErrors()) {
			logger.error("Validation error : " + baseModel);
			ResponseModel responseModel = new ResponseModel();
			responseModel.setResponseCodeFailure();
			responseModel.setMsg(ResponseMessageUtil.getMessage(result, baseController.getMessages(), baseController.getLocale()));
			return responseModel;
		}
		return pjp.proceed();
	}
}
