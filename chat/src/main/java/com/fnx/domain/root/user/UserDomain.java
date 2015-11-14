package com.fnx.domain.root.user;

import org.springframework.beans.factory.annotation.Configurable;

import com.fnx.db.entity.user.UserDBVO;

@Configurable
public class UserDomain {

	private UserDBVO data;
	
	public UserDomain() {
		
	}
	
	public UserDomain(UserDBVO data) {
		this.data = data;
	}
	
	//检查所传的id有没有在like列表里
	public boolean haveLiked(String id) {
		boolean result = false;
		for (String likeId : data.getLikes()) {
			if (id.equals(likeId)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
