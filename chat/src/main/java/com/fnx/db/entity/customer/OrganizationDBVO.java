package com.fnx.db.entity.customer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fnx.db.entity.BaseEntity;
import com.fnx.db.vo.customer.OrganizationEmployeeVO;

@Document(collection = "organization")
public class OrganizationDBVO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String tenantId;	
	private String name;
	private String phone;
	private String website;
	private String contacts;
	private String contactsMobile;
	private String sex;
	private String wechat;
	private String qq;
	private String email;
	private String other;
	
	private Set<OrganizationEmployeeVO> employees = new HashSet<OrganizationEmployeeVO>();
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Set<OrganizationEmployeeVO> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<OrganizationEmployeeVO> employees) {
		this.employees = employees;
	}
}
