package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DonXuat;


@Repository
public interface DonXuatRepository extends JpaRepository<DonXuat, Long>,JpaSpecificationExecutor<DonXuat>{

}
