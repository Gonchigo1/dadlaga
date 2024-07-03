package com.example.test.model;

import jakarta.validation.constraints.*;

public class UsersDto {

	@NotEmpty(message="id is required")
	private String id;
	@NotEmpty(message="username is required")
	private String username;
	
	@NotEmpty(message="email is required")
	private String email;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
