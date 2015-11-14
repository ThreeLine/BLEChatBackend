package com.fnx.db.vo.customer;

public class PersonStatureVO {
	
	public static final String BACK_STRAIGHT = "0";
	public static final String BACK_AVERAGE = "1";
	public static final String BACK_BEND = "2";
	
	public static final String SHOULDER_STRAIGHT = "0";
	public static final String SHOULDER_AVERAGE = "1";
	public static final String SHOULDER_WRY = "2";
	
	public static final String STOMACH_FLAT = "0";
	public static final String STOMACH_AVERAGE = "1";
	public static final String STOMACH_BIG = "2";
	
	public static final String FIGURE_SLIM = "0";
	public static final String FIGURE_AVERAGE = "1";
	public static final String FIGURE_STRONG = "2";
	public static final String FIGURE_FAT = "3";
	
	//身高
	private String height;
	private String weight;
	private String back;
	private String shoulder;
	//肚
	private String tripe;
	//身
	private String body;
	
	
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
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
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

