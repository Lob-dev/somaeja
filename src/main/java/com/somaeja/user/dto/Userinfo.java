package com.somaeja.user.dto;

import com.somaeja.user.entity.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Userinfo {

	private final Long id;
	private final RoleStatus role;

}
