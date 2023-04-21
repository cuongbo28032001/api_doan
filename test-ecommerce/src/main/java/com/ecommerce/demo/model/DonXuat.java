package com.ecommerce.demo.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "DonXuat")
public class DonXuat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 10, min = 3)
	@Column(name="ma_don_xuat", unique = true)
	private String maDonXuat;

	@Temporal(TemporalType.DATE)
	@Column(name="ngay_xuat")
	private Date ngayXuat;

	@Column(name="nguoi_xuat_id")
	private String nguoiXuatId;

	//bi-directional many-to-one association to AppChitietdonxuat
	@OneToMany(mappedBy="donXuat")
	private List<ChiTietDonXuat> chitietdonxuats;

	//bi-directional many-to-one association to AppThongkexuat
	@OneToMany(mappedBy="donXuat")
	private List<AppThongkexuat> thongkexuats;
	
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
	
	

}