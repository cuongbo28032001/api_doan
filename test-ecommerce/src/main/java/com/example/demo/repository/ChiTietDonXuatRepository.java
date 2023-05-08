package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ChiTietDonXuat;


@Repository
public interface ChiTietDonXuatRepository extends JpaRepository<ChiTietDonXuat, Long>, JpaSpecificationExecutor<ChiTietDonXuat>{

}
