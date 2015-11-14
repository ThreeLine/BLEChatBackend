package com.fnx.repository.mongo.tenant;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fnx.db.entity.tenant.TenantUserDBVO;

public interface TenantUserAutoRepo extends MongoRepository<TenantUserDBVO, String> {

	Optional<TenantUserDBVO> findByEmail(String email);
}
