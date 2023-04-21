package com.ecommerce.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * The persistent class for the app_thongkenhap database table.
 * 
 */
@Entity
@Table(name="app_thongkenhap")
@Data
public class AppThongkenhap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int tiennhap;

	//bi-directional many-to-one association to AppDonnhap
	@ManyToOne
	@JoinColumn(name="don_nhap_id")
	private DonNhap donNhap;
	
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

	public AppThongkenhap() {
	}

}