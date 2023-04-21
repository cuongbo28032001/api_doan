package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.model.Thuoc;

@Repository
public interface ThuocRepository extends JpaRepository<Thuoc, Long>,JpaSpecificationExecutor<Thuoc>{

}
