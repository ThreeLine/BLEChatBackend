package com.fnx.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.HtmlUtils;

import com.fnx.db.entity.tenant.TenantUserDBVO;
import com.fnx.repository.mongo.tenant.TenantUserAutoRepo;
import com.fnx.webapp.model.common.UserDetailsModel;
import com.fnx.webapp.util.ResponseMessageUtil;
import com.jayway.jsonpath.JsonPath;

@ContextConfiguration(locations = { "classpath:/web_init.xml", "/WEB-INF/dispatcher-servlet.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseControllerTestCase extends AbstractJUnit4SpringContextTests {

	protected String sessionTenantUserId = "55eff106d4c6394ac1f87688";
	
	@Autowired
	@Qualifier("basicMongoTemplate")
	protected MongoTemplate basicMongoTemplate;
	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	private TenantUserAutoRepo tenantUserAutoRepo;
	
	protected MockMvc mockMvc;

	protected MessageSourceAccessor messages;
	protected String SUCCESS_MESSAGE;

	@Autowired
	@Qualifier("resourceMessageSource")
	public void setMessages(MessageSource messageSource) {
		messages = new MessageSourceAccessor(messageSource);
	}

	protected String getMessage(String key, String... args) {
		String message = ResponseMessageUtil.getMessage(messages, Locale.US, key, args);
		if (args.length == 0) {
			return message;
		} else {
			return HtmlUtils.htmlEscape(message);
		}
	}

	/**
	 * Convenience methods to make tests simpler
	 * 
	 * @return a MockHttpServletRequest with a POST to the specified URL
	 * @param url
	 *            the URL to post to
	 */
	public MockHttpServletRequest newPost(String url) {
		return new MockHttpServletRequest("POST", url);
	}

	public MockHttpServletRequest newGet(String url) {
		return new MockHttpServletRequest("GET", url);
	}

	@Before
	public void setup() {
		DefaultMockMvcBuilder webAppContextSetup = MockMvcBuilders.webAppContextSetup(this.wac);
		webAppContextSetup.alwaysDo(MockMvcResultHandlers.print());
		webAppContextSetup.alwaysExpect(MockMvcResultMatchers.status().isOk());
		this.mockMvc = webAppContextSetup.build();

		this.SUCCESS_MESSAGE = this.getMessage("success.operation");
		basicMongoTemplate.getDb().dropDatabase();
	}
	
	protected Authentication loadAuthentication(String userId) {
		TenantUserDBVO tenantUserDBVO = tenantUserAutoRepo.findOne(userId);
		UserDetailsModel userDetail = new UserDetailsModel();
		userDetail.setId(tenantUserDBVO.getId());
		userDetail.setPassword(tenantUserDBVO.getPassword());
		userDetail.setUsername(tenantUserDBVO.getEmail());
		userDetail.setDisplayName(tenantUserDBVO.getName());
		userDetail.setTenantId(tenantUserDBVO.getTenantId());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetail, userDetail.getUsername(), null);
		token.setDetails(userDetail);
		SecurityContextHolder.getContext().setAuthentication(token);
		return token;
	}

	protected Object retrieveDataFromResponse(String field, ResultActions actions) throws UnsupportedEncodingException {
		MvcResult mvcResult = actions.andReturn();
		JsonPath jsonPath = JsonPath.compile(field);
		return jsonPath.read(mvcResult.getResponse().getContentAsString());
	}
	
	protected void init_sessionTenantUser() {
		TenantUserDBVO tenantUserDBVO = new TenantUserDBVO();
		tenantUserDBVO.setId(sessionTenantUserId);
		tenantUserDBVO.setEmail("admin@3fnx.com");
		tenantUserDBVO.setPassword("866a39abf9c5b724862a22453bb64c0620830aefaf8a2bad0e5fe7d8c115c029c38259af4b425190");
		tenantUserDBVO.setName("admin");
		tenantUserDBVO.setTenantId("55eff106d4c6394ac1f87681");
		this.basicMongoTemplate.insert(tenantUserDBVO);
	}	

}
