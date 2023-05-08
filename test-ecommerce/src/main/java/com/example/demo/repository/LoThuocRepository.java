package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LoThuoc;
import com.example.demo.model.Thuoc;

@Repository
public interface LoThuocRepository extends JpaRepository<LoThuoc, Long>,JpaSpecificationExecutor<LoThuoc>{
	List<LoThuoc> findByThuoc(Thuoc thuoc);
}
