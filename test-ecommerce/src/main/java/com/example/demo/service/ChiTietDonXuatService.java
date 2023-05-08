package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ChiTietDonXuat;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.request.ChiTietDonXuatRequest;
import com.example.demo.payload.response.ResponseObj;

public interface ChiTietDonXuatService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(List<ChiTietDonXuatRequest> chiTietDonXuats);
	ResponseObj update(ChiTietDonXuat chiTietDonXuat);
	ResponseObj delete(String id);

}
