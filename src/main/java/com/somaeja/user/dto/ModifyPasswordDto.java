package com.somaeja.user.dto;

import com.somaeja.user.utils.SHA256Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPasswordDto {

	private Long id;

	private String password;

	public String getEncodedPassword() {
		return SHA256Utils.encode(password);
	}
}
