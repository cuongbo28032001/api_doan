package com.example.demo.payload.request;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoThuocRequest {
	
	@NotNull
	@NotBlank
	private String soLo;
	
	@NotNull
	@NotBlank
	private Long thuoc;
	private BigDecimal giaNhap = BigDecimal.ZERO;
	private BigDecimal giaBan = BigDecimal.ZERO;
	private Date NSX;
	private Date HSD;
	

	public LoThuocRequest() {
		// TODO Auto-generated constructor stub
	}



	public LoThuocRequest(String soLo, Long thuoc) {
		super();
		this.soLo = soLo;
		this.thuoc = thuoc;
	}

}
