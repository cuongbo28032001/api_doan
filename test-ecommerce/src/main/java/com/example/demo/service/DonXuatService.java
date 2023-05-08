package com.example.demo.service;

import com.example.demo.model.DonXuat;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.response.ResponseObj;

public interface DonXuatService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(DonXuat donXuat);
	ResponseObj update(DonXuat donXuat);
	ResponseObj delete(String id);

}
