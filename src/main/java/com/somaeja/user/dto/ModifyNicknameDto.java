package com.somaeja.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyNicknameDto {

	@NotNull
	private Long id;

	@NotBlank
	private String nickname;

}
