package com.example.demo.service;

import java.util.List;

import com.example.demo.model.LoThuoc;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.request.LoThuocRequest;
import com.example.demo.payload.response.ResponseObj;

public interface LoThuocService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(LoThuocRequest loThuoc);
	ResponseObj update(LoThuoc loThuoc);
	ResponseObj delete(String id);
	ResponseObj create(List<LoThuoc> loThuocs);
}
