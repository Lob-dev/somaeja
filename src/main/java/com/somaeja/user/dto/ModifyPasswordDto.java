package com.somaeja.user.dto;

import com.somaeja.user.utils.SHA256Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPasswordDto {

	@NotNull
	private Long id;

	@NotBlank
	private String password;

	public String getEncodedPassword() {
		return SHA256Utils.encode(password);
	}
}
