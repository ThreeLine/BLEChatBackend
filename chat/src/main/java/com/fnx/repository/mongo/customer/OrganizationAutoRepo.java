package com.fnx.repository.mongo.customer;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fnx.db.entity.customer.OrganizationDBVO;

public interface OrganizationAutoRepo extends MongoRepository<OrganizationDBVO, String> {

	Optional<OrganizationDBVO> findByTenantIdAndName(String tenantId, String name);
}
