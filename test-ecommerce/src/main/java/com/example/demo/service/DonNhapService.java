package com.example.demo.service;

import com.example.demo.model.DonNhap;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.response.ResponseObj;

public interface DonNhapService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(DonNhap donNhap);
	ResponseObj update(DonNhap donNhap);
	ResponseObj delete(String id);

}
