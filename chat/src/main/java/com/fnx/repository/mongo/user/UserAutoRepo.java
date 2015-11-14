package com.fnx.repository.mongo.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fnx.db.entity.user.UserDBVO;

public interface UserAutoRepo extends MongoRepository<UserDBVO, String>{

}
