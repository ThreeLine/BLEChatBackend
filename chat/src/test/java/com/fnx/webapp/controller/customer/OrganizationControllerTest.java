package com.fnx.webapp.controller.customer;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fnx.db.entity.customer.OrganizationDBVO;
import com.fnx.repository.mongo.customer.OrganizationAutoRepo;
import com.fnx.webapp.controller.BaseControllerTestCase;
import com.fnx.webapp.controller.utility.MockUtility;
import com.fnx.webapp.model.customer.OrganizationBasicModel;
import com.fnx.webapp.util.WebConstants;

public class OrganizationControllerTest extends BaseControllerTestCase {

	@Autowired
	private OrganizationAutoRepo organizationAutoRepo;

	@Test
	public void createBasic() throws Exception {
		this.init_sessionTenantUser();
		OrganizationBasicModel orgBasicModel = new OrganizationBasicModel();

		orgBasicModel.setName("name");
		orgBasicModel.setPhone("phone");
		orgBasicModel.setWebsite("website");
		orgBasicModel.setContacts("contacts");
		orgBasicModel.setContactsMobile("contactsMobile");
		orgBasicModel.setWechat("wechat");
		;
		orgBasicModel.setQq("qq");
		orgBasicModel.setEmail("ocean.yang@3fnx.com");
		orgBasicModel.setOther("other");

		// perform
		ResultActions actions = super.mockMvc
				.perform(MockUtility.populatePostBuilder("/customer/organization/basic", orgBasicModel)
						.principal(this.loadAuthentication(sessionTenantUserId)));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		List<OrganizationDBVO> orgs = organizationAutoRepo.findAll();
		OrganizationDBVO org = orgs.get(0);

		Assert.assertEquals("name", org.getName());
		Assert.assertEquals("phone", org.getPhone());
		Assert.assertEquals("website", org.getWebsite());
		Assert.assertEquals("contacts", org.getContacts());
		Assert.assertEquals("contactsMobile", org.getContactsMobile());
		Assert.assertEquals("wechat", org.getWechat());
		Assert.assertEquals("qq", org.getQq());
		Assert.assertEquals("ocean.yang@3fnx.com", org.getEmail());
		Assert.assertEquals("other", org.getOther());
	}
}
