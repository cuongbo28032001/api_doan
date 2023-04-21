package com.ecommerce.demo.service;

import com.ecommerce.demo.model.DonNhap;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;

public interface DonNhapService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(DonNhap donNhap);
	ResponseObj update(DonNhap donNhap);
	ResponseObj delete(String id);

}
