package com.fnx.webapp.model.common;

public class UploadImageModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String path;
	private String name;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
