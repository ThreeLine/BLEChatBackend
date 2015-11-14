package com.fnx.webapp.model.customer;

import java.util.ArrayList;
import java.util.List;

import com.fnx.webapp.model.PageableModel;

public class PersonSearchModel extends PageableModel {

	private static final long serialVersionUID = 1L;

	private String name;
	private String mobile;

	private List<PersonSearchRowVO> rows = new ArrayList<PersonSearchRowVO>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PersonSearchRowVO> getRows() {
		return rows;
	}

	public void setRows(List<PersonSearchRowVO> rows) {
		this.rows = rows;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
