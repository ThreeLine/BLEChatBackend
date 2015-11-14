package com.fnx.webapp.service.user;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fnx.db.entity.tenant.TenantUserDBVO;
import com.fnx.repository.mongo.tenant.TenantUserAutoRepo;
import com.fnx.webapp.model.common.UserDetailsModel;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private TenantUserAutoRepo tenantUserAutoRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (StringUtils.isBlank(email)) {
			throw new UsernameNotFoundException("user not found exception!");
		}
		Optional<TenantUserDBVO> userOptional = tenantUserAutoRepo.findByEmail(email);
		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException("user not found exception!");
		}
		UserDetailsModel userDetail = new UserDetailsModel();
		TenantUserDBVO user = userOptional.get();
		userDetail.setId(user.getId());
		userDetail.setPassword(user.getPassword());
		userDetail.setUsername(user.getEmail());
		userDetail.setDisplayName(user.getName());
		userDetail.setTenantId(user.getTenantId());
		return userDetail;
	}

}
