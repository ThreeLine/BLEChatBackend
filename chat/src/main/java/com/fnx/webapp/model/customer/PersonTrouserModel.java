package com.fnx.webapp.model.customer;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.fnx.webapp.model.BaseModel;

public class PersonTrouserModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	// 裤头
	@NotEmpty
	private String kutou;
	// 裤长
	@NotEmpty
	private String kuchang;
	// 坐围实
	private String zuoweishi;
	// 坐围抛
	private String zuoweipao;	
	// 裤髀实
	private String kubishi;
	// 裤髀抛
	private String kubipao;	
	// 内长
	private String neichang;
	// 上浪
	private String shanglang;
	// 总浪
	@NotEmpty
	private String zonglang;
	// 中膝
	@NotEmpty
	private String zhongxi;
	// 裤脚
	@NotEmpty
	private String kujiao;

	// 裁剪尺寸
	private Map<String, String> cuttingSize = new HashMap<String, String>();

	public String getKutou() {
		return kutou;
	}

	public void setKutou(String kutou) {
		this.kutou = kutou;
	}

	public String getKuchang() {
		return kuchang;
	}

	public void setKuchang(String kuchang) {
		this.kuchang = kuchang;
	}

	public String getNeichang() {
		return neichang;
	}

	public void setNeichang(String neichang) {
		this.neichang = neichang;
	}

	public String getShanglang() {
		return shanglang;
	}

	public void setShanglang(String shanglang) {
		this.shanglang = shanglang;
	}

	public String getZhongxi() {
		return zhongxi;
	}

	public void setZhongxi(String zhongxi) {
		this.zhongxi = zhongxi;
	}

	public String getKujiao() {
		return kujiao;
	}

	public void setKujiao(String kujiao) {
		this.kujiao = kujiao;
	}

	public Map<String, String> getCuttingSize() {
		return cuttingSize;
	}

	public void setCuttingSize(Map<String, String> cuttingSize) {
		this.cuttingSize = cuttingSize;
	}

	public String getZonglang() {
		return zonglang;
	}

	public void setZonglang(String zonglang) {
		this.zonglang = zonglang;
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

	public String getKubishi() {
		return kubishi;
	}

	public void setKubishi(String kubishi) {
		this.kubishi = kubishi;
	}

	public String getKubipao() {
		return kubipao;
	}

	public void setKubipao(String kubipao) {
		this.kubipao = kubipao;
	}

}
