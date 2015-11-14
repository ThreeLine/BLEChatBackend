package com.fnx.domain.factory;

import com.fnx.db.entity.user.UserDBVO;
import com.fnx.domain.root.user.UserDomain;

public interface DomainFactory {

	UserDomain buildUser(UserDBVO vo);
}
