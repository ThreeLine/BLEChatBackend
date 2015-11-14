package com.fnx.webapp.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fnx.webapp.util.WebConstants;

public  class PageableModel extends BaseModel implements Pageable {
	private static final long serialVersionUID = 1715463893175745619L;
	// first page is 0
	private int pageNumber;
	private int pageSize = WebConstants.DEFAULT_PAGE_SIZE;
	private String direction;
	private String property;
	private long total;

	public int getTotalPages() {
		return getPageSize() == 0 ? 0 : (int) Math.ceil((double) total / (double) getPageSize());
	}

	@Override
	public int getPageNumber() {
		return this.pageNumber;
	}

	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public int getOffset() {
		return this.pageNumber * this.pageSize;
	}

	@Override
	public Sort getSort() {
		if (StringUtils.isNotBlank(direction) && StringUtils.isNotBlank(property)) {
			return new Sort(Direction.fromString(this.direction), property);
		}
		return null;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	public boolean hasPrevious() {
		return (this.pageNumber > 0);
	}

	public Pageable next() {
		return new PageRequest(this.pageNumber + 1, this.pageSize, getSort());
	}

	public Pageable previousOrFirst() {
		return ((hasPrevious()) ? new PageRequest(this.pageNumber - 1, this.pageSize, getSort()) : this);
	}

	public Pageable first() {
		return new PageRequest(0, this.pageSize, getSort());
	}
}
