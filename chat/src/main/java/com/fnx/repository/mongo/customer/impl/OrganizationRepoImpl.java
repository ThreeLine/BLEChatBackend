package com.fnx.repository.mongo.customer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.fnx.db.entity.customer.OrganizationDBVO;
import com.fnx.repository.mongo.customer.OrganizationRepo;

@Repository
public class OrganizationRepoImpl implements OrganizationRepo {

	@Autowired
	@Qualifier("basicMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Override
	public Page<OrganizationDBVO> findOrganizations(String tenantId, String name, String phone, Pageable pageable) {
		Criteria criteria = new Criteria();
		Query userQuery = Query.query(
				criteria.orOperator(Criteria.where("name").regex(name, "i"), Criteria.where("phone").regex(phone, "i")).and("tenantId").is(tenantId)).with(pageable);
		long count = mongoTemplate.count(userQuery, OrganizationDBVO.class);
		List<OrganizationDBVO> list = mongoTemplate.find(userQuery, OrganizationDBVO.class);
		return new PageImpl<OrganizationDBVO>(list, pageable, count);
	}

}
