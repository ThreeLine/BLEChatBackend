package com.fnx.webapp.model.user;

import java.io.Serializable;

public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String sex;
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
