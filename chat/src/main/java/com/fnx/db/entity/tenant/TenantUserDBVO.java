package com.fnx.db.entity.tenant;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fnx.db.entity.BaseEntity;

@Document(collection = "tenantUser")
public class TenantUserDBVO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String password;
	private String name;
	private String email;
	private String department;
	private String position;
	private String tenantId;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
