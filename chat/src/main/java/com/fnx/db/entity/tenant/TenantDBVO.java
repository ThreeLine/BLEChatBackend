package com.fnx.db.entity.tenant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fnx.db.entity.BaseEntity;

@Document(collection = "tenant")
public class TenantDBVO extends BaseEntity {

	private static final long serialVersionUID = 779291318124891133L;

	public static final String GROUP_CODE_HK = "hk";
	public static final String GROUP_CODE_MA = "ma";
	public static final String GROUP_CODE_CN = "cn";
	public static final String GROUP_CODE_PM = "pm";
	
	public static final String UNIT_INCH = "inch";
	public static final String UNIT_CENTI = "centi";
	
	public static final String CURRENCY_RMB = "RMB";
	public static final String CURRENCY_HKD = "HKD";
	
	private String tenantCode;	
	private String fullName;
	private String shortName;
	private String address;
	private String tel;
	private String fax;
	private String email;
	//1.hk 2.ma 3.cn 4.pm
	private String groupCode;
	private String dimensionUnit;
	private String currency;
	
	private TenantContactDBVO manager;
	private List<TenantContactDBVO> contacts = new ArrayList<TenantContactDBVO>();
	
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getDimensionUnit() {
		return dimensionUnit;
	}
	public void setDimensionUnit(String dimensionUnit) {
		this.dimensionUnit = dimensionUnit;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public TenantContactDBVO getManager() {
		return manager;
	}
	public void setManager(TenantContactDBVO manager) {
		this.manager = manager;
	}
	public List<TenantContactDBVO> getContacts() {
		return contacts;
	}
	public void setContacts(List<TenantContactDBVO> contacts) {
		this.contacts = contacts;
	}

}

