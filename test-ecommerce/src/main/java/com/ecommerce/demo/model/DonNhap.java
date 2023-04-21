package com.ecommerce.demo.model;

import java.time.LocalDateTime;
import java.util.Date;
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

@Data
@Entity
@Table(name = "DonNhap")

public class DonNhap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 10, min = 3)
	@Column(name = "ma_don_nhap", unique = true)
	private String maDonNhap;

	@Column(name = "ngay_nhap")
	private Date ngayNhap;

	@Column(name = "nguoi_nhap")
	private String nguoiNhap;

	// bi-directional many-to-one association to AppChitietdonnhap
	@JsonIgnore
	@OneToMany(mappedBy = "donNhap")
	private List<ChiTietDonNhap> chitietdonnhaps;

	// bi-directional many-to-one association to AppThongkenhap
	@JsonIgnore
	@OneToMany(mappedBy = "donNhap")
	private List<AppThongkenhap> thongkenhaps;

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

	public DonNhap(Long id) {
		super();
		this.id = id;
	}

	public DonNhap() {
		super();
	}

}