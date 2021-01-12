package com.somaeja.user.dto;

import com.somaeja.user.entity.RoleStatus;
import com.somaeja.user.entity.User;
import com.somaeja.user.utils.SHA256Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

	@NotEmpty
	private String location;

	@NotEmpty
	private String nickName;

	@NotEmpty
	private String password;

	@NotEmpty
	private String email;

	@NotEmpty
	private String phoneNumber;

	public User toEntity(Long locationId) {
		return User.builder()
			.locationId(locationId)
			.nickName(nickName)
			.password(SHA256Utils.encode(password))
			.email(email)
			.phoneNumber(phoneNumber)
			.role(RoleStatus.getRoleUser())
			.createdDate(LocalDateTime.now())
			.modifyDate(LocalDateTime.now())
			.build();
	}
}
