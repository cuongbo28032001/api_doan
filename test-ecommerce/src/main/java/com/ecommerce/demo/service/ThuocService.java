package com.ecommerce.demo.service;

import com.ecommerce.demo.model.Thuoc;
import com.ecommerce.demo.payload.request.BaseRequest;
import com.ecommerce.demo.payload.response.ResponseObj;

public interface ThuocService {
	
	ResponseObj getByID(String id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj create(Thuoc thuoc);
	ResponseObj update(Thuoc thuoc);
	ResponseObj delete(String id);

}
