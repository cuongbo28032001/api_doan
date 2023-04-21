package com.ecommerce.demo.service;

import com.ecommerce.demo.model.DonXuat;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;

public interface DonXuatService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(DonXuat donXuat);
	ResponseObj update(DonXuat donXuat);
	ResponseObj delete(String id);

}
