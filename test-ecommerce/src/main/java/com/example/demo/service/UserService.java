package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.response.ResponseObj;

public interface UserService {
	
	ResponseObj update(User user);
	ResponseObj status(Long id);
	ResponseObj search(BaseRequest baseRequest);
	ResponseObj getById(Long id);

}
