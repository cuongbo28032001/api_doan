package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "LoThuoc",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"soLo", "thuoc_id"})
    })
public class LoThuoc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 1, max = 20)
	private String soLo;
	
	@ManyToOne
	@JoinColumn(name = "thuoc_id")
	private Thuoc thuoc;
	
	private String tenThuoc;
	private BigDecimal giaNhap = BigDecimal.ZERO;
	private BigDecimal giaBan = BigDecimal.ZERO;
	
	private Date NSX;
	private Date HSD;
	private int soLuong = 0;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSoLo() {
		return soLo;
	}

	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}

	public Thuoc getThuoc() {
		return thuoc;
	}

	public void setThuoc(Thuoc thuoc) {
		this.thuoc = thuoc;
	}

	public String getTenThuoc() {
		tenThuoc =  this.thuoc.getTenThuoc();
		return tenThuoc;
	}

	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}

	public BigDecimal getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(BigDecimal giaNhap) {
		this.giaNhap = giaNhap;
	}

	public BigDecimal getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(BigDecimal giaBan) {
		this.giaBan = giaBan;
	}

	public Date getNSX() {
		return NSX;
	}

	public void setNSX(Date nSX) {
		NSX = nSX;
	}

	public Date getHSD() {
		return HSD;
	}

	public void setHSD(Date hSD) {
		HSD = hSD;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public LoThuoc() {
	}

	public LoThuoc(Long id) {
		this.id = id;
	}

}
