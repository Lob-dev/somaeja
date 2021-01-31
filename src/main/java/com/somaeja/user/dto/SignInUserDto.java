package com.somaeja.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignInUserDto {

	@Email
	private String email;

	@NotEmpty
	private String password;

}
