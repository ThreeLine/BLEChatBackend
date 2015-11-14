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

import com.fnx.db.entity.customer.PersonDBVO;
import com.fnx.repository.mongo.customer.PersonRepo;

@Repository
public class PersonRepoImpl implements PersonRepo {

	@Autowired
	@Qualifier("basicMongoTemplate")
	private MongoTemplate mongoTemplate;
	

	@Override
	public Page<PersonDBVO> findPersons(String tenantId, String name, String mobile, Pageable pageable) {
		Criteria criteria = new Criteria();
		Query userQuery = Query.query(
				criteria.andOperator(Criteria.where("name").regex(name, "i"), Criteria.where("mobile").regex(mobile, "i")).and("tenantId").is(tenantId)).with(pageable);
		long count = mongoTemplate.count(userQuery, PersonDBVO.class);
		List<PersonDBVO> list = mongoTemplate.find(userQuery, PersonDBVO.class);
		return new PageImpl<PersonDBVO>(list, pageable, count);
	}

}
