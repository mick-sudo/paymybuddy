package com.mick.paymybuddy.dto;

import javax.validation.constraints.Email;

import com.sun.istack.NotNull;

public class UserRegistrationDto {

	private String firstname;
	private String lastname;
	private String password;
	private String email;

	public UserRegistrationDto() {
	}

	public UserRegistrationDto(String firstName, String lastname, String password, @Email @NotNull String email) {
		this.firstname = firstName;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
