package com.fnx.webapp.model.customer;

import org.hibernate.validator.constraints.NotEmpty;

import com.fnx.webapp.model.BaseModel;

public class PersonStatureModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	// 身高
	private String height;
	private String weight;
	@NotEmpty
	private String back;
	@NotEmpty
	private String shoulder;
	// 肚
	@NotEmpty
	private String tripe;
	// 身
	@NotEmpty
	private String body;

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getShoulder() {
		return shoulder;
	}

	public void setShoulder(String shoulder) {
		this.shoulder = shoulder;
	}

	public String getTripe() {
		return tripe;
	}

	public void setTripe(String tripe) {
		this.tripe = tripe;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
