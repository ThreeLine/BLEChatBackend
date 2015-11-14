package com.fnx.repository.mongo.customer;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fnx.db.entity.customer.PersonDBVO;

public interface PersonAutoRepo extends MongoRepository<PersonDBVO, String> {

	Optional<PersonDBVO> findByTenantIdAndEmail(String tenantId, String email);
	
	Optional<PersonDBVO> findByTenantIdAndMobile(String tenantId, String mobile);
	
}
