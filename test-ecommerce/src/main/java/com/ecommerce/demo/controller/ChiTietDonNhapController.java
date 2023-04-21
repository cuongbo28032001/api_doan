package com.ecommerce.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.model.ChiTietDonNhap;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;
import com.ecommerce.demo.service.ChiTietDonNhapService;

@RestController
@RequestMapping("/api/chi-tiet-don-nhap")
public class ChiTietDonNhapController {

	@Autowired
	private ChiTietDonNhapService chiTietDonNhapService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ChiTietDonNhap chiTietDonNhap) {
		ResponseObj resp = this.chiTietDonNhapService.create(chiTietDonNhap);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ChiTietDonNhap chiTietDonNhap) {
		ResponseObj resp = this.chiTietDonNhapService.update(chiTietDonNhap);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		ResponseObj resp = this.chiTietDonNhapService.delete(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		ResponseObj resp = this.chiTietDonNhapService.getByID(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody BaseRequest baseRequest) {
		ResponseObj resp = this.chiTietDonNhapService.search(baseRequest);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	public ChiTietDonNhapController() {
		// TODO Auto-generated constructor stub
	}

}
