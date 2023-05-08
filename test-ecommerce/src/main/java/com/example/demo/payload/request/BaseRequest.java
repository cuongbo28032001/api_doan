package com.example.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequest {

	private Integer pageNumber;
	private Integer pageSize;
	private Object customize;

	public BaseRequest(int pageNumber, int pageSize, Object customize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.customize = customize;
	}

	public BaseRequest() {
		super();
	}

}
