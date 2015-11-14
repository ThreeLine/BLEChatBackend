package com.fnx.repository.mongo.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fnx.db.entity.customer.PersonDBVO;

public interface PersonRepo {

	Page<PersonDBVO> findPersons(String tenantId, String name, String mobile, Pageable pageable);
}
