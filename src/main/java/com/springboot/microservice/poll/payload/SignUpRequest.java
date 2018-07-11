package com.springboot.microservice.poll.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

	@NotBlank
	@Size(min = 4, max = 40)
	private String name = null;

	@NotBlank
	@Size(min = 4, max = 40)
	private String username = null;

	@NotBlank
	@Size(min = 4, max = 40)
	@Email
	private String email = null;

	@NotBlank
	@Size(min = 4, max = 40)
	private String password = null;

	public SignUpRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
