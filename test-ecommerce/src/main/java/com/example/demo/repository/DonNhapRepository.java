package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DonNhap;


@Repository
public interface DonNhapRepository extends JpaRepository<DonNhap, Long>, JpaSpecificationExecutor<DonNhap>{

}
