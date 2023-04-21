package com.ecommerce.demo.service;

import com.ecommerce.demo.model.ChiTietDonXuat;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;

public interface ChiTietDonXuatService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(ChiTietDonXuat chiTietDonXuat);
	ResponseObj update(ChiTietDonXuat chiTietDonXuat);
	ResponseObj delete(String id);

}
