package com.fnx.db.vo.customer;

import java.util.HashMap;
import java.util.Map;

public class PersonSkirtVO {

	//裙头
	private String quntou;
	//裙长
	private String qunchang;
	//坐围实
	private String zuoweishi;
	//坐围抛
	private String zuoweipao;	
	//裙脚
	private String qunjiao;
	
	//裁剪尺寸
	private Map<String, String> cuttingSize = new HashMap<String, String>();

	public String getQuntou() {
		return quntou;
	}

	public void setQuntou(String quntou) {
		this.quntou = quntou;
	}

	public String getQunchang() {
		return qunchang;
	}

	public void setQunchang(String qunchang) {
		this.qunchang = qunchang;
	}

	public String getQunjiao() {
		return qunjiao;
	}

	public void setQunjiao(String qunjiao) {
		this.qunjiao = qunjiao;
	}

	public Map<String, String> getCuttingSize() {
		return cuttingSize;
	}

	public void setCuttingSize(Map<String, String> cuttingSize) {
		this.cuttingSize = cuttingSize;
	}

	public String getZuoweishi() {
		return zuoweishi;
	}

	public void setZuoweishi(String zuoweishi) {
		this.zuoweishi = zuoweishi;
	}

	public String getZuoweipao() {
		return zuoweipao;
	}

	public void setZuoweipao(String zuoweipao) {
		this.zuoweipao = zuoweipao;
	}
	
}

