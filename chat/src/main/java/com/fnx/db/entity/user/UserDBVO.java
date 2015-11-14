package com.fnx.db.entity.user;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fnx.db.entity.BaseEntity;

@Document(collection = "tenant")
public class UserDBVO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public final static String SEX_FEMALE = "female";
	public final static String SEX_MALE = "male";

	private String name;
	private String sex = UserDBVO.SEX_FEMALE;
	private int age;
	private String imagePath;

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
}
