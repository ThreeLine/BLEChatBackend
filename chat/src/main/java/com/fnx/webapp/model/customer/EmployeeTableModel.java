package com.fnx.webapp.model.customer;

import java.util.ArrayList;
import java.util.List;

import com.fnx.webapp.model.BaseModel;

public class EmployeeTableModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private List<EmployeeRowVO> employeeRows = new ArrayList<EmployeeRowVO>();

	public List<EmployeeRowVO> getEmployeeRows() {
		return employeeRows;
	}

	public void setEmployeeRows(List<EmployeeRowVO> employeeRows) {
		this.employeeRows = employeeRows;
	}

}
