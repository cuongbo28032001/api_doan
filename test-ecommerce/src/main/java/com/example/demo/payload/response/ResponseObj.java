package com.example.demo.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseObj {

	private String code;
	private String desc;
	private Object result;

	public ResponseObj() {
		// TODO Auto-generated constructor stub
	}

	public ResponseObj(String code, String desc, Object result) {
		super();
		this.code = code;
		this.desc = desc;
		this.result = result;
	}

	public ResponseObj(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}

}
