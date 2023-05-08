package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ChiTietDonNhap;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.request.ChiTietDonNhapRequest;
import com.example.demo.payload.response.ResponseObj;

public interface ChiTietDonNhapService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(List<ChiTietDonNhapRequest> chiTietDonNhapRequests);
	ResponseObj update(ChiTietDonNhap chiTietDonNhap);
	ResponseObj delete(String id);

}
