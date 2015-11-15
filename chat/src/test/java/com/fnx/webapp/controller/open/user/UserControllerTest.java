package com.fnx.webapp.controller.open.user;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fnx.db.entity.user.UserDBVO;
import com.fnx.repository.mongo.user.UserAutoRepo;
import com.fnx.webapp.controller.BaseControllerTestCase;
import com.fnx.webapp.controller.utility.MockUtility;
import com.fnx.webapp.model.user.LikePersonModel;
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
	
	private void init_loadPersion() {
		UserDBVO userDBVO = new UserDBVO();
		userDBVO.setAge(18);
		userDBVO.setId("555ff106d4c6394ac1f87688");
		userDBVO.setName("Ocean");
		userDBVO.setSex(UserDBVO.SEX_MALE);
		userDBVO.setImagePath("788.jpg");
		userDBVO.setStatus(UserDBVO.STATUS_BUSY);
		this.basicMongoTemplate.insert(userDBVO);		
	}
	
	@Test
	public void loadPersion() throws Exception {
		init_loadPersion();
		ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders.get("/open/person/555ff106d4c6394ac1f87688.json"));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.name").value("Ocean"));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.age").value(18));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.id").value("555ff106d4c6394ac1f87688"));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.sex").value(UserDBVO.SEX_MALE));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.imagePath").value("788.jpg"));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.status").value(UserDBVO.STATUS_BUSY));
	}
	
	private void init_changeToBusy() {
		UserDBVO userDBVO = new UserDBVO();
		userDBVO.setId("555ff106d4c6394ac1f87688");
		userDBVO.setStatus(UserDBVO.STATUS_READY);
		this.basicMongoTemplate.insert(userDBVO);
	}
	
	@Test
	public void changeToBusy() throws Exception {
		init_changeToBusy();
		// perform
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/open/person/555ff106d4c6394ac1f87688/changeToBusy.json", null));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		UserDBVO customerDBVO = userAutoRepo.findOne("555ff106d4c6394ac1f87688");
		
		Assert.assertEquals(UserDBVO.STATUS_BUSY, customerDBVO.getStatus());		
	}
	
	private void init_changeToReady() {
		UserDBVO userDBVO = new UserDBVO();
		userDBVO.setId("555ff106d4c6394ac1f87688");
		userDBVO.setStatus(UserDBVO.STATUS_BUSY);
		this.basicMongoTemplate.insert(userDBVO);
	}
	
	@Test
	public void changeToReady() throws Exception {
		init_changeToReady();
		// perform
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/open/person/555ff106d4c6394ac1f87688/changeToReady.json", null));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		UserDBVO customerDBVO = userAutoRepo.findOne("555ff106d4c6394ac1f87688");
		
		Assert.assertEquals(UserDBVO.STATUS_READY, customerDBVO.getStatus());		
	}	
	
	private void init_likeYou_bothLike() {
		List<UserDBVO> users = new ArrayList<UserDBVO>();
		
		UserDBVO currentUser = new UserDBVO();
		currentUser.setId("555ff106d4c6394ac1f87688");
		currentUser.setName("current");
		
		UserDBVO anotherUser = new UserDBVO();
		anotherUser.setId("555ff106d4c6394ac1f87677");
		anotherUser.setName("another");
		anotherUser.getLikes().add("555ff106d4c6394ac1f87688");
		
		users.add(currentUser);
		users.add(anotherUser);
		this.basicMongoTemplate.insertAll(users);
	}
	
	@Test
	public void likeYou_bothLike() throws Exception {
		init_likeYou_bothLike();
		LikePersonModel likePersonModel = new LikePersonModel();
		likePersonModel.setId("555ff106d4c6394ac1f87677");
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/open/person/555ff106d4c6394ac1f87688/like", likePersonModel));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.bothLike").value(true));
	}
	
	private void init_likeYou_singleLike() {
		List<UserDBVO> users = new ArrayList<UserDBVO>();
		
		UserDBVO currentUser = new UserDBVO();
		currentUser.setId("555ff106d4c6394ac1f87688");
		currentUser.setName("current");
		
		UserDBVO anotherUser = new UserDBVO();
		anotherUser.setId("555ff106d4c6394ac1f87677");
		anotherUser.setName("another");
		
		users.add(currentUser);
		users.add(anotherUser);
		this.basicMongoTemplate.insertAll(users);		
	}
	
	
	@Test
	public void likeYou_singleLike() throws Exception {
		init_likeYou_singleLike();
		LikePersonModel likePersonModel = new LikePersonModel();
		likePersonModel.setId("555ff106d4c6394ac1f87677");
		ResultActions actions = super.mockMvc.perform(MockUtility.populatePutBuilder("/open/person/555ff106d4c6394ac1f87688/like", likePersonModel));
		actions.andExpect(MockMvcResultMatchers.jsonPath("code").value(WebConstants.RESPONSE_CODE_SUCCESS));
		actions.andExpect(MockMvcResultMatchers.jsonPath("data.bothLike").value(false));		
		
	}
	
	private void init_likeYou_likeSelf() {
		List<UserDBVO> users = new ArrayList<UserDBVO>();
		
		UserDBVO currentUser = new UserDBVO();
		currentUser.setId("555ff106d4c6394ac1f87688");
		currentUser.setName("current");
		
		users.add(currentUser);
		this.basicMongoTemplate.insertAll(users);			
	}
	
}
