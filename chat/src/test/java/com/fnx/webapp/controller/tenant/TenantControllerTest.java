package com.fnx.webapp.controller.tenant;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fnx.db.entity.tenant.TenantDBVO;
import com.fnx.repository.mongo.tenant.TenantAutoRepo;
import com.fnx.webapp.controller.BaseControllerTestCase;
import com.fnx.webapp.util.WebConstants;

public class TenantControllerTest extends BaseControllerTestCase {

	@Autowired
	private TenantAutoRepo tenantAutoRepo;
	
	private void init_loadTenants() {
		TenantDBVO tenant = new TenantDBVO();
		tenant.setFullName("full name");
		tenant.setShortName("short name");
		tenant.setTenantCode("tenantCode");
		this.tenantAutoRepo.save(tenant);
	}
	
	@Test
	public void loadTenants() throws Exception {
		init_loadTenants();
		ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders.get("/tenants.json"));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data").exists());
	}
}
