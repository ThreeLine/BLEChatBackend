package com.fnx.webapp.model.customer;

import org.hibernate.validator.constraints.NotEmpty;

import com.fnx.webapp.model.BaseModel;

public class OrganizationEmployeeBasicModel extends BaseModel {


	private static final long serialVersionUID = 1L;
	private String id;
	private String sex;
	@NotEmpty
	private String name;
	private String department;
	private String position;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
