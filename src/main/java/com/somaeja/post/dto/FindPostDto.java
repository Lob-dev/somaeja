package com.somaeja.post.dto;

import com.somaeja.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FindPostDto {

	private Long id;
	private String title;
	private String content;
	private Long price;
	private Long imageId;
	private Long userId;
	private Long locationId;

	public static FindPostDto of(Post post) {
		return FindPostDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.price(post.getPrice())
			.imageId(post.getImageId())
			.userId(post.getUserId())
			.locationId(post.getLocationId())
			.build();
	}
}
