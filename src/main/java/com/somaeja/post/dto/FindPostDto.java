package com.somaeja.post.dto;

import com.somaeja.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FindPostDto {

	private final Long id;
	private final String title;
	private final String content;
	private final String location;
	private final Long price;
	private final Long imageId;
	private final Long userId;
	public static FindPostDto of(Post post) {
		return FindPostDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.location(post.getCityCountryTown())
			.price(post.getPrice())
			.imageId(post.getImageId())
			.userId(post.getUserId())
			.build();
	}
}
