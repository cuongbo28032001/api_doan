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
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "DonXuat")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DonXuat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 15, min = 3)
	@Column(name="ma_don_xuat", unique = true)
	private String maDonXuat;

	@Column(name="ngay_xuat")
	private Date ngayXuat;

	@Column(name="nguoi_xuat_id")
	private String nguoiXuat;
	
	@Column(name = "tong_tien")
	private BigDecimal tongTien = BigDecimal.ZERO;

	//bi-directional many-to-one association to AppChitietdonxuat
    @OneToMany(mappedBy="donXuat")
	private List<ChiTietDonXuat> chitietdonxuats;

	//bi-directional many-to-one association to AppThongkexuat
	@OneToMany(mappedBy="donXuat", fetch = FetchType.EAGER)
	private List<AppThongkexuat> thongkexuats;
	
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
	
	private String generateMaDonXuat() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd-HHmmss");
	    return "DX" +formatter.format(new Date());
	}
	
	@PrePersist
	public void prePersist() {
	    this.maDonXuat = generateMaDonXuat();
	}

	public DonXuat() {
		super();
	}

	public DonXuat(Long id) {
		super();
		this.id = id;
	}

}