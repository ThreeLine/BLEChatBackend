package com.fnx.webapp.model.user;

import java.io.Serializable;

public class LikePersonModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	//双方都like过
	private boolean bothLike = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isBothLike() {
		return bothLike;
	}

	public void setBothLike(boolean bothLike) {
		this.bothLike = bothLike;
	}
}
