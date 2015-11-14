package com.fnx.repository.mongo.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fnx.db.entity.customer.OrganizationDBVO;

public interface OrganizationRepo {

	Page<OrganizationDBVO> findOrganizations(String tenantId, String name, String phone, Pageable pageable);
	
}
