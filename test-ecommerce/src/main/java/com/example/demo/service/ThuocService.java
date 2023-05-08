package com.example.demo.service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Thuoc;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.response.ResponseObj;

public interface ThuocService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(Thuoc thuoc);
	ResponseObj update(Thuoc thuoc);
	ResponseObj delete(String id);
	ResponseObj create(MultipartFile file, String id);
	Optional<Thuoc> findById(Long id);
	ResponseObj getImage(Long id);

}
