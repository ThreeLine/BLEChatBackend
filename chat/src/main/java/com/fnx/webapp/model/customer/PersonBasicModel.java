package com.fnx.webapp.model.customer;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fnx.webapp.model.BaseModel;

public class PersonBasicModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private String id;
	// 1为male 0为female
	private String sex;
	@NotEmpty
	private String name;
	@NotEmpty
	private String mobile;
	private String wechat;
	private String qq;
	@Email(message = "personBasicModel.email.invalid")
	private String email;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
