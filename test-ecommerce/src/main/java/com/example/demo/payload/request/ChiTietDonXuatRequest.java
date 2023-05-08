package com.example.demo.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChiTietDonXuatRequest {
	
	@NotNull
	@NotBlank
	private int soLuong;
	
	@NotNull
	@NotBlank
	private Long donXuat;
	
	@NotNull
	@NotBlank
	private Long loThuoc;

	public ChiTietDonXuatRequest() {
		// TODO Auto-generated constructor stub
	}

}
