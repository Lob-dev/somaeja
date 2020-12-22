package com.somaeja.post.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostInfo {
	private Long id;
	private String detailMessage;

	public static PostInfo from(Long id, String detailMessage) {
		return new PostInfo(id, detailMessage);
	}
}
