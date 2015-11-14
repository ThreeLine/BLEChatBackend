package com.fnx.webapp.controller.open.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fnx.db.entity.tenant.TenantDBVO;
import com.fnx.db.entity.user.UserDBVO;
import com.fnx.repository.mongo.tenant.TenantAutoRepo;
import com.fnx.repository.mongo.user.UserAutoRepo;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.model.saas.TenantModel;
import com.fnx.webapp.model.user.UserModel;

@RestController
@RequestMapping("/open")
public class UserController {

	@Autowired
	private UserAutoRepo userAutoRepo;
	
	@RequestMapping(value = "/person/basic", method = RequestMethod.POST)
	public ResponseModel createBasic(@RequestBody @Valid UserModel model) {
		ResponseModel resModel = new ResponseModel();
		UserDBVO userDBVO = new UserDBVO();
		userDBVO.setAge(model.getAge());
		userDBVO.setImagePath(model.getImagePath());
		userDBVO.setName(model.getName());
		userDBVO.setSex(model.getSex());
		userAutoRepo.save(userDBVO);
		model.setId(userDBVO.getId());
		return resModel;
	}
	
	
	@Autowired
	private TenantAutoRepo tenantAutoRepo;

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ResponseModel loadTenants() {
		ResponseModel resModel = new ResponseModel();
		List<TenantDBVO> tenantDBVOs = tenantAutoRepo.findAll();
		if (!tenantDBVOs.isEmpty()) {
			List<TenantModel> tenants = new ArrayList<TenantModel>();
			for (TenantDBVO tenantDBVO : tenantDBVOs) {
				TenantModel tenantModel = new TenantModel();
				tenantModel.setId(tenantDBVO.getId());
				tenantModel.setName(tenantDBVO.getFullName());
				tenants.add(tenantModel);
			}
			resModel.setData(tenants);
		}
		return resModel;
	}	
}
