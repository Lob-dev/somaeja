package com.somaeja.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyMailDto {

	@NotNull
	private Long id;

	@NotEmpty
	private String email;

}
