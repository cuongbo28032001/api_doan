package com.ecommerce.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "Thuoc")
@Data
public class Thuoc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "bao_quan")
	private String baoQuan;

	@Column(name = "chong_chi_dinh")
	private String chongChiDinh;

	@Column(name = "cong_dung")
	private String congDung;

	@Column(name = "dang_bao_che")
	private String dangBaoChe;

	@Column(name = "doi_tuong_su_dung")
	private String doiTuongSuDung;

	@Column(name = "don_vi_tinh")
	private String donViTinh;

	@Column(name = "nha_san_xuat")
	private String nhaSanXuat;

	@Column(name = "hoat_tinh_thuoc")
	private String hoatTinhThuoc;

	@Column(name = "huong_dan_su_dung")
	private String huongDanSuDung;

	@Column(name = "lieu_luong")
	private String lieuLuong;

	@Size(max = 10, min = 3)
	@Column(name = "ma_thuoc", unique = true)
	private String maThuoc;

	@Column(name = "tac_dung_phu")
	private String tacDungPhu;

	@Column(name = "ten_thuoc")
	private String tenThuoc;

	@Column(name = "thanh_phan")
	private String thanhPhan;

	@Column(name = "loai_thuoc")
	private String loaiThuoc;

	@Column(name = "tinh_trang_khong_nen_su_dung")
	private String tinhTrangKhongNenSuDung;

	@Column(name = "tuong_tac_thuoc")
	private String tuongTacThuoc;

	// bi-directional many-to-one association to AppChitietdonnhap
	@JsonIgnore
	@OneToMany(mappedBy = "thuoc")
	private List<ChiTietDonNhap> chitietdonnhaps;

	// bi-directional many-to-one association to AppChitietdonxuat
	@JsonIgnore
	@OneToMany(mappedBy = "thuoc")
	private List<ChiTietDonXuat> chitietdonxuats;

	// bi-directional many-to-one association to AppTonkho
	@JsonIgnore
	@OneToMany(mappedBy = "thuoc")
	private List<Kho> khos;

	// bi-directional many-to-one association to AppTonkho
	@JsonIgnore
	@OneToMany(mappedBy = "thuoc")
	private List<HinhAnhThuoc> hinhAnhThuocs;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@LastModifiedDate
	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

	@LastModifiedBy
	@Column(name = "updated_by", insertable = false)
	private String updatedBy;

	public Thuoc(Long id) {
		super();
		this.id = id;
	}

	public Thuoc() {
		super();
	}

}
