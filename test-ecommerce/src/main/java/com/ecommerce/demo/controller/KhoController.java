package com.ecommerce.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.model.Kho;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;
import com.ecommerce.demo.service.KhoService;

@RestController
@RequestMapping("/api/kho")
@CrossOrigin(origins = "*", maxAge = 3600)
public class KhoController {
	
	@Autowired
	private KhoService khoService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Kho kho) {
		ResponseObj resp = this.khoService.create(kho);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Kho kho) {
		ResponseObj resp = this.khoService.update(kho);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		ResponseObj resp = this.khoService.delete(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		ResponseObj resp = this.khoService.getByID(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody BaseRequest baseRequest) {
		ResponseObj resp = this.khoService.search(baseRequest);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}

	public KhoController() {
		// TODO Auto-generated constructor stub
	}

}
