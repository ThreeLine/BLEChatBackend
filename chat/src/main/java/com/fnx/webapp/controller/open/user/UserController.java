package com.fnx.webapp.controller.open.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fnx.db.entity.user.UserDBVO;
import com.fnx.domain.factory.DomainFactory;
import com.fnx.domain.root.user.UserDomain;
import com.fnx.repository.mongo.user.UserAutoRepo;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.model.user.LikePersonModel;
import com.fnx.webapp.model.user.UserModel;
import com.fnx.webapp.util.WebConstants;

@RestController
@RequestMapping("/open")
public class UserController {

	@Autowired
	private UserAutoRepo userAutoRepo;
	@Autowired
	private DomainFactory domainFactory;
	
	@RequestMapping(value = "/person/basic", method = RequestMethod.POST)
	public ResponseModel createBasic(@RequestBody UserModel model) {
		ResponseModel resModel = new ResponseModel();
		UserDBVO userDBVO = new UserDBVO();
		userDBVO.setAge(model.getAge());
		userDBVO.setName(model.getName());
		userDBVO.setSex(model.getSex());
		userAutoRepo.save(userDBVO);
		model.setId(userDBVO.getId());
		resModel.setData(model);
		return resModel;
	}
	
	@RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
	public ResponseModel updateBasic(@RequestBody UserModel model, @PathVariable String id) {
		System.out.println(model.getImagePath());
		ResponseModel resModel = new ResponseModel();
		UserDBVO userDBVO = userAutoRepo.findOne(id);
		if (userDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		userDBVO.setImagePath(model.getImagePath());
		userAutoRepo.save(userDBVO);
		System.out.println("finish save imagePath " + model.getImagePath());
		return resModel;
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
	public ResponseModel loadPersion(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		UserDBVO userDBVO = userAutoRepo.findOne(id);
		if (userDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}		
		UserModel model = new UserModel();
		model.setAge(userDBVO.getAge());
		model.setId(userDBVO.getId());
		model.setImagePath(userDBVO.getImagePath());
		model.setName(userDBVO.getName());
		model.setSex(userDBVO.getSex());
		model.setStatus(userDBVO.getStatus());
		resModel.setData(model);
		return resModel;
	}
	
	@RequestMapping(value = "/person/{id}/changeToBusy", method = RequestMethod.PUT)
	public ResponseModel changeToBusy(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		UserDBVO userDBVO = userAutoRepo.findOne(id);
		if (userDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}	
		userDBVO.setStatus(UserDBVO.STATUS_BUSY);
		userAutoRepo.save(userDBVO);
		return resModel;
	}
	
	@RequestMapping(value = "/person/{id}/changeToReady", method = RequestMethod.PUT)
	public ResponseModel changeToReady(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		UserDBVO userDBVO = userAutoRepo.findOne(id);
		if (userDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}	
		userDBVO.setStatus(UserDBVO.STATUS_READY);
		userAutoRepo.save(userDBVO);
		return resModel;		
	}	
	
	@RequestMapping(value = "/person/{id}/like", method = RequestMethod.PUT)
	public ResponseModel likeYou(@RequestBody LikePersonModel model, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		UserDBVO currentUser = userAutoRepo.findOne(id);
		if (currentUser == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		UserDBVO anotherUser = userAutoRepo.findOne(model.getId());
		if (anotherUser == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}		
		currentUser.getLikes().add(model.getId());
		userAutoRepo.save(currentUser);

		
		UserDomain anotherUserDomain = domainFactory.buildUser(anotherUser);
		boolean anotherLike = anotherUserDomain.haveLiked(id);
		if (anotherLike) {
			model.setBothLike(true);
		}
		resModel.setData(model);
		return resModel;
	}
}
