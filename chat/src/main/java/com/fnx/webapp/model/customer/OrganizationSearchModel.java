package com.fnx.webapp.model.customer;

import java.util.ArrayList;
import java.util.List;

import com.fnx.webapp.model.PageableModel;

public class OrganizationSearchModel extends PageableModel {

	private static final long serialVersionUID = 1L;

	private String name;
	private String phone;
	
	private List<OrganizationSearchRowVO> rows = new ArrayList<OrganizationSearchRowVO>();

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

	public List<OrganizationSearchRowVO> getRows() {
		return rows;
	}

	public void setRows(List<OrganizationSearchRowVO> rows) {
		this.rows = rows;
	}
	
}
