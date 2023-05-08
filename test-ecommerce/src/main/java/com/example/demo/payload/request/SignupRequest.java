package com.example.demo.payload.request;

import java.util.Date;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 100)
	private String name;
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 60)
	@Email
	private String email;
	
	private Set<String> role;
	
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
	private String cccd;
	private int sex;
	private Date birthDay;
	private String phone;

}
