package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Thuoc")

public class Thuoc {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBaoQuan() {
		return baoQuan;
	}

	public void setBaoQuan(String baoQuan) {
		this.baoQuan = baoQuan;
	}

	public String getChongChiDinh() {
		return chongChiDinh;
	}

	public void setChongChiDinh(String chongChiDinh) {
		this.chongChiDinh = chongChiDinh;
	}

	public String getCongDung() {
		return congDung;
	}

	public void setCongDung(String congDung) {
		this.congDung = congDung;
	}

	public String getDangBaoChe() {
		return dangBaoChe;
	}

	public void setDangBaoChe(String dangBaoChe) {
		this.dangBaoChe = dangBaoChe;
	}

	public String getDoiTuongSuDung() {
		return doiTuongSuDung;
	}

	public void setDoiTuongSuDung(String doiTuongSuDung) {
		this.doiTuongSuDung = doiTuongSuDung;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public String getNhaSanXuat() {
		return nhaSanXuat;
	}

	public void setNhaSanXuat(String nhaSanXuat) {
		this.nhaSanXuat = nhaSanXuat;
	}

	public String getHoatTinhThuoc() {
		return hoatTinhThuoc;
	}

	public void setHoatTinhThuoc(String hoatTinhThuoc) {
		this.hoatTinhThuoc = hoatTinhThuoc;
	}

	public String getHuongDanSuDung() {
		return huongDanSuDung;
	}

	public void setHuongDanSuDung(String huongDanSuDung) {
		this.huongDanSuDung = huongDanSuDung;
	}

	public String getLieuLuong() {
		return lieuLuong;
	}

	public void setLieuLuong(String lieuLuong) {
		this.lieuLuong = lieuLuong;
	}

	public String getMaThuoc() {
		return maThuoc;
	}

	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}

	public String getTacDungPhu() {
		return tacDungPhu;
	}

	public void setTacDungPhu(String tacDungPhu) {
		this.tacDungPhu = tacDungPhu;
	}

	public String getTenThuoc() {
		return tenThuoc;
	}

	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}

	public String getThanhPhan() {
		return thanhPhan;
	}

	public void setThanhPhan(String thanhPhan) {
		this.thanhPhan = thanhPhan;
	}

	public String getLoaiThuoc() {
		return loaiThuoc;
	}

	public void setLoaiThuoc(String loaiThuoc) {
		this.loaiThuoc = loaiThuoc;
	}

	public String getTinhTrangKhongNenSuDung() {
		return tinhTrangKhongNenSuDung;
	}

	public void setTinhTrangKhongNenSuDung(String tinhTrangKhongNenSuDung) {
		this.tinhTrangKhongNenSuDung = tinhTrangKhongNenSuDung;
	}

	public String getTuongTacThuoc() {
		return tuongTacThuoc;
	}

	public void setTuongTacThuoc(String tuongTacThuoc) {
		this.tuongTacThuoc = tuongTacThuoc;
	}

	@JsonIgnore
    private boolean firstRecursion = true;
	public List<LoThuoc> getLoThuoc() {
		List<LoThuoc> result = this.loThuoc;
	if (!firstRecursion) {
            result = null;
        }
        firstRecursion = false;
        return result;
	}

	public void setLoThuoc(List<LoThuoc> loThuoc) {
		this.loThuoc = loThuoc;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "image")
	private String image;

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
	
	@OneToMany(mappedBy = "thuoc", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"loThuoc"}) // loại bỏ trường loThuoc khỏi phép đệ quy
	private List<LoThuoc> loThuoc;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private Date createdAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@LastModifiedDate
	@Column(name = "updated_at", insertable = false)
	private Date updatedAt;

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
