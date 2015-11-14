package com.fnx.db.entity.customer;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fnx.db.entity.BaseEntity;
import com.fnx.db.vo.customer.PersonSkirtVO;
import com.fnx.db.vo.customer.PersonStatureVO;
import com.fnx.db.vo.customer.PersonSuitVO;
import com.fnx.db.vo.customer.PersonTrouserVO;
import com.fnx.db.vo.customer.PersonTshirtVO;
import com.fnx.db.vo.customer.PersonVestVO;

@Document(collection = "person")
public class PersonDBVO extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String tenantId;
	private String sex;
	private String name;
	private String mobile;
	private String wechat;
	private String qq;
	private String email;
	private String other;

	private PersonStatureVO customerStatureVO = new PersonStatureVO();
	private PersonSuitVO customerSuitVO = new PersonSuitVO();
	private PersonTrouserVO customerTrouserVO = new PersonTrouserVO();
	private PersonSkirtVO customerSkirtVO = new PersonSkirtVO();
	private PersonVestVO customerVestVO = new PersonVestVO();
	private PersonTshirtVO customerTshirtVO = new PersonTshirtVO();

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public PersonStatureVO getCustomerStatureVO() {
		return customerStatureVO;
	}

	public void setCustomerStatureVO(PersonStatureVO customerStatureVO) {
		this.customerStatureVO = customerStatureVO;
	}

}
