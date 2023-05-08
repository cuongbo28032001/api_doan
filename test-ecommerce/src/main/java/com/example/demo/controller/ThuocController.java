package com.example.demo.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Thuoc;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.response.ResponseObj;
import com.example.demo.service.FileStoreService;
import com.example.demo.service.ThuocService;


@RestController
@RequestMapping("/api/thuoc")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ThuocController {
	
	@Autowired
	private ThuocService thuocService;
	
	@Autowired
	private FileStoreService fileStoreService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Thuoc thuoc) {
		ResponseObj resp = this.thuocService.create(thuoc);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Thuoc thuoc) {
		ResponseObj resp = this.thuocService.update(thuoc);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		ResponseObj resp = this.thuocService.delete(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		ResponseObj resp = this.thuocService.getByID(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody BaseRequest baseRequest) {
		ResponseObj resp = this.thuocService.search(baseRequest);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
			@RequestParam("id") String id) {
		ResponseObj resp = this.thuocService.create(file, id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@GetMapping("/image/{id}")
	public ResponseEntity<Resource> getMedicineImage(@PathVariable Long id) {
	    // Retrieve the medicine from the database by ID
	    Optional<Thuoc> optionalMedicine = thuocService.findById(id);
		Thuoc thuoc = null;
	    try {
	    	thuoc = optionalMedicine.get();
	    } catch (NoSuchElementException e) {
	    	return ResponseEntity.notFound().build();
	    }
	    // Get the image from the file store
	    Resource file = fileStoreService.load(thuoc.getImage());
	    // Return the image
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
	            .body(file);
	
	}

	public ThuocController() {
		// TODO Auto-generated constructor stub
	}

}
