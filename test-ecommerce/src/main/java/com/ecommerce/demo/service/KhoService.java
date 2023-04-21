package com.ecommerce.demo.service;

import com.ecommerce.demo.model.Kho;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;

public interface KhoService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(Kho kho);
	ResponseObj update(Kho kho);
	ResponseObj delete(String id);

}
