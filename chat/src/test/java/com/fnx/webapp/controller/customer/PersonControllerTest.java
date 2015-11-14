package com.fnx.webapp.controller.customer;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fnx.db.entity.EntityConstants;
import com.fnx.db.entity.customer.PersonDBVO;
import com.fnx.db.vo.customer.PersonStatureVO;
import com.fnx.db.vo.customer.PersonSuitVO;
import com.fnx.repository.mongo.customer.PersonAutoRepo;
import com.fnx.webapp.controller.BaseControllerTestCase;
import com.fnx.webapp.controller.utility.MockUtility;
import com.fnx.webapp.model.ModelConstants;
import com.fnx.webapp.model.customer.PersonBasicModel;
import com.fnx.webapp.model.customer.PersonStatureModel;
import com.fnx.webapp.model.customer.PersonSuitModel;
import com.fnx.webapp.util.WebConstants;


public class PersonControllerTest extends BaseControllerTestCase {

	@Autowired
	private PersonAutoRepo customerAutoRepo;
	
	@Test
	public void createBasic() throws Exception {
		init_sessionTenantUser();
		
		PersonBasicModel personModel = new PersonBasicModel();
		personModel.setEmail("user@3fnx.com");
		personModel.setMobile("13751118192");
		personModel.setName("user");
		personModel.setQq("433");
		personModel.setSex(ModelConstants.SEX_FEMALE);
		personModel.setWechat("wewe");
		// perform
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePostBuilder("/customer/person/basic", personModel).principal(this.loadAuthentication(sessionTenantUserId)));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		
		List<PersonDBVO> customerList = customerAutoRepo.findAll();
		PersonDBVO customerOne = customerList.get(0);
		
		Assert.assertEquals("13751118192", customerOne.getMobile());
		Assert.assertEquals("user", customerOne.getName());
		Assert.assertEquals("433", customerOne.getQq());
		Assert.assertEquals(EntityConstants.SEX_FEMALE, customerOne.getSex());
		Assert.assertEquals("wewe", customerOne.getWechat());
	}
	
	private void init_updateBasic() {
		PersonDBVO customer = new PersonDBVO();
		customer.setId("555ff106d4c6394ac1f87688");
		customer.setEmail("user@3fnx.com");
		customer.setMobile("13751118199");
		customer.setName("Tonny");
		customer.setWechat("wewe");
		customer.setQq("43363");
		customer.setSex(EntityConstants.SEX_MALE);
		customerAutoRepo.insert(customer);
	}
	
	@Test
	public void updateBasic() throws Exception {
		init_sessionTenantUser();
		init_updateBasic();
		
		PersonBasicModel personModel = new PersonBasicModel();
		personModel.setEmail("update@3fnx.com");
		personModel.setMobile("13751118192");
		personModel.setName("updateUser");
		personModel.setQq("433");
		personModel.setSex(ModelConstants.SEX_FEMALE);
		personModel.setWechat("wewe");		
		
		// perform
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/customer/person/555ff106d4c6394ac1f87688/basic", personModel).principal(this.loadAuthentication(sessionTenantUserId)));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		PersonDBVO customerDBVO = customerAutoRepo.findOne("555ff106d4c6394ac1f87688");
		
		Assert.assertEquals("update@3fnx.com", customerDBVO.getEmail());
		Assert.assertEquals("13751118192", customerDBVO.getMobile());
		Assert.assertEquals("updateUser", customerDBVO.getName());
		Assert.assertEquals("433", customerDBVO.getQq());
		Assert.assertEquals(EntityConstants.SEX_FEMALE, customerDBVO.getSex());
		Assert.assertEquals("wewe", customerDBVO.getWechat());
	}
	
	@Test
	public void deletePerson() throws Exception {
		init_updateBasic();
		ResultActions actions = super.mockMvc.perform(MockUtility.populateDeleteBuilder("/customer/person/555ff106d4c6394ac1f87688", null));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		PersonDBVO customerDBVO = customerAutoRepo.findOne("555ff106d4c6394ac1f87688");
		Assert.assertNull(customerDBVO);
	}
	
	@Test
	public void updateStature() throws Exception {
		init_updateBasic();
		PersonStatureModel personStatureModel = new PersonStatureModel();
		personStatureModel.setHeight("1.7");
		personStatureModel.setWeight("120");
		personStatureModel.setBack("1");
		personStatureModel.setShoulder("2");
		personStatureModel.setTripe("0");
		personStatureModel.setBody("3");
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/customer/person/555ff106d4c6394ac1f87688/stature", personStatureModel));		
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		PersonDBVO customerDBVO = customerAutoRepo.findOne("555ff106d4c6394ac1f87688");
		PersonStatureVO customerStatureVO = customerDBVO.getCustomerStatureVO();
		Assert.assertEquals("1.7", customerStatureVO.getHeight());
		Assert.assertEquals("120", customerStatureVO.getWeight());
		Assert.assertEquals("1", customerStatureVO.getBack());
		Assert.assertEquals("2", customerStatureVO.getShoulder());
		Assert.assertEquals("0", customerStatureVO.getTripe());
		Assert.assertEquals("3", customerStatureVO.getBody());
	}
	
	@Test
	public void updateSuit() throws Exception {
		init_updateBasic();
		
		PersonSuitModel personSuitModel = new PersonSuitModel();
		personSuitModel.setQianchang("qianchang");
		personSuitModel.setHouzhongchang("houzhongchange");
		personSuitModel.setShangweishi("shangweishi");
		personSuitModel.setQianxiongkuo("Qianxiongkuo");
		personSuitModel.setHoubei("houbei");
		personSuitModel.setJiankuo("jiankuo");
		personSuitModel.setQianxiaojian("Qianxiaojian");
		personSuitModel.setXiuchang("xiuchang");
		personSuitModel.setXiukou("xiukou");
		personSuitModel.setXionggao("xionggao");
		personSuitModel.setYaozhi("yaozhi");
		personSuitModel.setLingwei("lingwei");
		personSuitModel.getCuttingSize().put("B2", "33");
		
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/customer/person/555ff106d4c6394ac1f87688/suit", personSuitModel));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		
		PersonDBVO customerDBVO = customerAutoRepo.findOne("555ff106d4c6394ac1f87688");
		PersonSuitVO customerSuitVO = customerDBVO.getCustomerSuitVO();
		
		Assert.assertEquals("qianchang", customerSuitVO.getQianchang());
		Assert.assertEquals("houzhongchange", customerSuitVO.getHouzhongchange());
		Assert.assertEquals("Qianxiongkuo", customerSuitVO.getQianxiongkuo());
		Assert.assertEquals("houbei", customerSuitVO.getHoubei());
		Assert.assertEquals("jiankuo", customerSuitVO.getJiankuo());
		Assert.assertEquals("Qianxiaojian", customerSuitVO.getQianxiaojian());
		Assert.assertEquals("xiuchang", customerSuitVO.getXiuchang());
		Assert.assertEquals("xiukou", customerSuitVO.getXiukou());
		Assert.assertEquals("xionggao", customerSuitVO.getXionggao());
		Assert.assertEquals("yaozhi", customerSuitVO.getYaozhi());
		Assert.assertEquals("lingwei", customerSuitVO.getLingwei());
		Assert.assertEquals("33", customerSuitVO.getCuttingSize().get("B2"));
		
	}
}
