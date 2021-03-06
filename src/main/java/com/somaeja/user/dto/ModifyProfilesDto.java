package com.somaeja.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyProfilesDto {

	private Long id;
	private String nickname;
	private String password;
	private String email;

}
