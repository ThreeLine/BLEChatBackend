package com.fnx.db.vo.customer;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OrganizationEmployeeVO {

	private String id;
	private String name;
	private String sex;
	private String department;
	private String position;

	private PersonStatureVO customerStatureVO = new PersonStatureVO();
	private PersonSuitVO customerSuitVO = new PersonSuitVO();
	private PersonTrouserVO customerTrouserVO = new PersonTrouserVO();
	private PersonSkirtVO customerSkirtVO = new PersonSkirtVO();
	private PersonVestVO customerVestVO = new PersonVestVO();
	private PersonTshirtVO customerTshirtVO = new PersonTshirtVO();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public PersonStatureVO getCustomerStatureVO() {
		return customerStatureVO;
	}

	public void setCustomerStatureVO(PersonStatureVO customerStatureVO) {
		this.customerStatureVO = customerStatureVO;
	}

	public PersonSuitVO getCustomerSuitVO() {
		return customerSuitVO;
	}

	public void setCustomerSuitVO(PersonSuitVO customerSuitVO) {
		this.customerSuitVO = customerSuitVO;
	}

	public PersonTrouserVO getCustomerTrouserVO() {
		return customerTrouserVO;
	}

	public void setCustomerTrouserVO(PersonTrouserVO customerTrouserVO) {
		this.customerTrouserVO = customerTrouserVO;
	}

	public PersonSkirtVO getCustomerSkirtVO() {
		return customerSkirtVO;
	}

	public void setCustomerSkirtVO(PersonSkirtVO customerSkirtVO) {
		this.customerSkirtVO = customerSkirtVO;
	}

	public PersonVestVO getCustomerVestVO() {
		return customerVestVO;
	}

	public void setCustomerVestVO(PersonVestVO customerVestVO) {
		this.customerVestVO = customerVestVO;
	}

	public PersonTshirtVO getCustomerTshirtVO() {
		return customerTshirtVO;
	}

	public void setCustomerTshirtVO(PersonTshirtVO customerTshirtVO) {
		this.customerTshirtVO = customerTshirtVO;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? UUID.randomUUID().hashCode() : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (id == null) {
			return false;
		} else if (!id.equals(((OrganizationEmployeeVO) obj).id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
