package com.example.demo.payload.request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ChiTietDonNhapRequest {
	
	private Long donNhap;
	private Long loThuoc;
	private int soLuong;
	private BigDecimal giaNhap;
	private BigDecimal giaBan;
	private Date nsx;
	private Date hsd;

	public ChiTietDonNhapRequest() {
		// TODO Auto-generated constructor stub
	}

}
