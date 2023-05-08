package com.example.demo.controller;

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

import com.example.demo.model.LoThuoc;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.request.LoThuocRequest;
import com.example.demo.payload.response.ResponseObj;
import com.example.demo.service.FileStoreService;
import com.example.demo.service.LoThuocService;


@RestController
@RequestMapping("/api/lothuoc")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoThuocController {
	
	@Autowired
	private LoThuocService loThuocService;

	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody LoThuocRequest loThuocRequest) {
		ResponseObj resp = this.loThuocService.create(loThuocRequest);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody LoThuoc loThuoc) {
		ResponseObj resp = this.loThuocService.update(loThuoc);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		ResponseObj resp = this.loThuocService.delete(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		ResponseObj resp = this.loThuocService.getByID(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody BaseRequest baseRequest) {
		ResponseObj resp = this.loThuocService.search(baseRequest);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	public LoThuocController() {
		// TODO Auto-generated constructor stub
	}

}
