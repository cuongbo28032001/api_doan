package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Faq;


public interface FaqRepository extends JpaRepository<Faq, Long> {

}
