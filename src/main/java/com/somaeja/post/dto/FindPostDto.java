package com.somaeja.post.dto;

import com.somaeja.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FindPostDto {

	private Long postId;
	private String title;
	private String content;
	private Long price;
	private Long imageId;
	private Long userId;

	public static FindPostDto of(Post post) {
		return FindPostDto.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.price(post.getPrice())
			.imageId(post.getImageId())
			.userId(post.getUserId())
			.build();
	}

}
