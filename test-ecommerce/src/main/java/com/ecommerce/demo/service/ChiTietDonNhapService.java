package com.ecommerce.demo.service;

import com.ecommerce.demo.model.ChiTietDonNhap;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;

public interface ChiTietDonNhapService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(ChiTietDonNhap chiTietDonNhap);
	ResponseObj update(ChiTietDonNhap chiTietDonNhap);
	ResponseObj delete(String id);

}
