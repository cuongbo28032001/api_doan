package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ChiTietDonNhap;


@Repository
public interface ChiTietDonNhapRepository extends JpaRepository<ChiTietDonNhap, Long>, JpaSpecificationExecutor<ChiTietDonNhap>{

}
