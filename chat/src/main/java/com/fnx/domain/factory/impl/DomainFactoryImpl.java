package com.fnx.domain.factory.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fnx.db.entity.user.UserDBVO;
import com.fnx.domain.factory.DomainFactory;
import com.fnx.domain.root.user.UserDomain;

@Service
public class DomainFactoryImpl implements DomainFactory {

	@Override
	public UserDomain buildUser(UserDBVO vo) {
		Assert.notNull(vo);
		UserDomain result = new UserDomain(vo);
		return result;
	}
}
