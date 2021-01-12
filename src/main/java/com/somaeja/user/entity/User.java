package com.somaeja.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class User {

	private final Long id;
	private final Long locationId;
	private final String nickName;
	private final String password;
	private final String email;
	private final String phoneNumber;
	// 계정 권한
	private final String role;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifyDate;
	private final String loaction;

}
