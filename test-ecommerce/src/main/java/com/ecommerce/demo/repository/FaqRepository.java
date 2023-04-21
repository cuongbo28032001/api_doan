package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.model.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {

}
