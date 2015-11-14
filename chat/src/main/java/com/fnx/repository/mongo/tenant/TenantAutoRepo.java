package com.fnx.repository.mongo.tenant;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fnx.db.entity.tenant.TenantDBVO;

public interface TenantAutoRepo extends MongoRepository<TenantDBVO, String> {

}
