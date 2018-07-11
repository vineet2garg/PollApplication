package com.springboot.microservice.poll.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank
	private String usernameOrEmail = null;

	@NotBlank
	private String password = null;

	public LoginRequest() {
		super();
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginRequest [usernameOrEmail=")
				.append(usernameOrEmail)
				.append(", password=")
				.append(password)
				.append("]");
		return builder.toString();
	}

}
