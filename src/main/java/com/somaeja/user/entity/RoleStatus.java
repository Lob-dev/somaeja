package com.somaeja.user.entity;

import lombok.Getter;

public enum RoleStatus {

	ROLE_USER, ROLE_ADMIN;

	public static String getRoleUser() {
		return ROLE_USER.toString();
	}

	public static String getRoleAdmin() {
		return ROLE_ADMIN.toString();
	}

}
