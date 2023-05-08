package com.example.demo.model;

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
@Table(name = "ChiTietDonXuat",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lo_thuoc_id", "don_xuat_id"})
    })
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ChiTietDonXuat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="so_luong")
	private int soLuong;

	//bi-directional many-to-one association to AppDonxuat
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="don_xuat_id")
	private DonXuat donXuat;

	//bi-directional many-to-one association to AppThuoc
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="lo_thuoc_id")
	private LoThuoc loThuoc;
	
	public ChiTietDonXuat(int soLuong, DonXuat donXuat, LoThuoc loThuoc) {
		super();
		this.soLuong = soLuong;
		this.donXuat = donXuat;
		this.loThuoc = loThuoc;
	}

	public ChiTietDonXuat() {
		super();
	}

	public ChiTietDonXuat(Long id) {
		super();
		this.id = id;
	}

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

}