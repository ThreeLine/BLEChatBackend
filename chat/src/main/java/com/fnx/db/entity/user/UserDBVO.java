package com.fnx.db.entity.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fnx.db.entity.BaseEntity;

@Document(collection = "userObject")
public class UserDBVO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public final static String SEX_FEMALE = "female";
	public final static String SEX_MALE = "male";
	
	public final static String STATUS_BUSY = "busy";
	public final static String STATUS_READY = "ready";

	private String name;
	private String sex = UserDBVO.SEX_FEMALE;
	private String status = UserDBVO.STATUS_READY;
	private int age;
	private String imagePath;
	//当前用户所like过的人
	private Set<String> likes = new HashSet<String>();	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<String> getLikes() {
		return likes;
	}

	public void setLikes(Set<String> likes) {
		this.likes = likes;
	}
}
