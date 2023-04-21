package com.ecommerce.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThuocRequest {
	private String baoQuan;

	private String chongChiDinh;

	private String congDung;

	private String dangBaoChe;

	private String doiTuongSuDung;

	private String donViTinh;

	private String nhaSanXuat;

	private String hoatTinhThuoc;

	private String huongDanSuDung;

	private String lieuLuong;

	private String maThuoc;

	private String tacDungPhu;

	private String tenThuoc;

	private String thanhPhan;

	private String loaiThuoc;

	private String tinhTrangKhongNenSuDung;

	private String tuongTacThuoc;

	public ThuocRequest() {
		super();
	}
	
}
