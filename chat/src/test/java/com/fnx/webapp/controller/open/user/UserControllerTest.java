package com.fnx.webapp.controller.open.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fnx.db.entity.user.UserDBVO;
import com.fnx.repository.mongo.user.UserAutoRepo;
import com.fnx.webapp.controller.BaseControllerTestCase;
import com.fnx.webapp.controller.utility.MockUtility;
import com.fnx.webapp.model.user.UserModel;
import com.fnx.webapp.util.WebConstants;

public class UserControllerTest extends BaseControllerTestCase {

	@Autowired
	private UserAutoRepo userAutoRepo;
	
	private void init_updateBasic() {
		UserDBVO userDBVO = new UserDBVO();
		userDBVO.setAge(18);
		userDBVO.setId("555ff106d4c6394ac1f87688");
		userDBVO.setName("Ocean");
		userDBVO.setSex(UserDBVO.SEX_MALE);
		this.basicMongoTemplate.insert(userDBVO);
	}
	
	
	@Test
	public void updateBasic() throws Exception {
		init_updateBasic();
		
		UserModel personModel = new UserModel();
		personModel.setImagePath("788.jpg");
		
		// perform
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/open/person/555ff106d4c6394ac1f87688", personModel));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		UserDBVO customerDBVO = userAutoRepo.findOne("555ff106d4c6394ac1f87688");
		
		Assert.assertEquals("788.jpg", customerDBVO.getImagePath());
	}
}
