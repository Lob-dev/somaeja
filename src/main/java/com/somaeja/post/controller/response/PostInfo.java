package com.somaeja.post.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostInfo {
	private Long postId;
	private String message;

	public static PostInfo from(Long id, String message) {
		return new PostInfo(id, message);
	}
}
