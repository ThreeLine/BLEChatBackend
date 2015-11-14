package com.fnx.webapp.controller.open.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fnx.db.entity.tenant.TenantDBVO;
import com.fnx.repository.mongo.tenant.TenantAutoRepo;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.model.saas.TenantModel;

@RestController
@RequestMapping("/open")
public class UserController {

	@Autowired
	private TenantAutoRepo tenantAutoRepo;
	
	@RequestMapping(value = "/tenants", method = RequestMethod.GET)
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
