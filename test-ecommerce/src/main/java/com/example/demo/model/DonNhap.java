package com.example.demo.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "DonNhap")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DonNhap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 15, min = 3)
	@Column(name = "ma_don_nhap", unique = true)
	private String maDonNhap;

	@Column(name = "ngay_nhap")
	private Date ngayNhap;

	@Column(name = "nguoi_nhap")
	private String nguoiNhap;
	
	@Column(name = "tong_tien")
	private BigDecimal tongTien = BigDecimal.ZERO;

	@OneToMany(mappedBy = "donNhap")
	private List<ChiTietDonNhap> chitietdonnhaps;

	// bi-directional many-to-one association to AppThongkenhap
	@OneToMany(mappedBy = "donNhap", fetch = FetchType.EAGER)
	private Set<AppThongkenhap> thongkenhaps;

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
	
	private String generateMaDonNhap() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd-HHmmss");
	    return "DN" +formatter.format(new Date());
	}
	
	@PrePersist
	public void prePersist() {
	    this.maDonNhap = generateMaDonNhap();
	}

	public DonNhap(Long id) {
		this.id = id;
	}

	public DonNhap() {
		super();
	}

}