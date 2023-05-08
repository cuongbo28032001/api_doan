package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "ChiTietDonNhap",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lo_thuoc_id", "don_nhap_id"})
    })
@Data
public class ChiTietDonNhap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="gia_nhap")
	private BigDecimal giaNhap;
	
	@Column(name="gia_ban")
	private BigDecimal giaBan;

	@Column(name="so_luong")
	private int soLuong;
	
	@Column(name = "han_su_dung")
	private Date hsd;
	private Date nsx;

	//bi-directional many-to-one association to AppDonnhap
	@ManyToOne()
	@JoinColumn(name="don_nhap_id")
	private DonNhap donNhap;

	//bi-directional many-to-one association to AppThuoc
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="lo_thuoc_id")
	private LoThuoc loThuoc;
	
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

	public ChiTietDonNhap() {
	}

	public ChiTietDonNhap(Long id) {
		super();
		this.id = id;
	}

	public ChiTietDonNhap(BigDecimal giaNhap, BigDecimal giaBan, int soLuong, Date hsd, Date nsx, DonNhap donNhap,
			LoThuoc loThuoc) {
		super();
		this.giaNhap = giaNhap;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
		this.hsd = hsd;
		this.nsx = nsx;
		this.donNhap = donNhap;
		this.loThuoc = loThuoc;
	}
	
	


}