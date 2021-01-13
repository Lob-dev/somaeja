package com.somaeja.user.dto;

import com.somaeja.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class FindUserDto {

	private String nickname;
	private String location;
	private String email;
	private LocalDateTime createDate;

	public static FindUserDto of(User user) {
		return FindUserDto.builder()
			.nickname(user.getNickName())
			.location(user.getLocation())
			.email(user.getEmail())
			.createDate(user.getCreatedDate())
			.build();
	}

}
