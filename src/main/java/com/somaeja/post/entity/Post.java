package com.somaeja.post.entity;

import com.somaeja.post.dto.CreatePostDto;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Builder @Getter
public class Post {

	private Long id;
	private String title;
	private String content;
	private Long price;
	// User 정보
	private Long userId;
	// Location 정보
	private Long locationId;
	// Image Path 정보
	private Long imageId;
	private boolean isNegotiable;
	private boolean isOfflineTrade;
	private LocalDateTime createdDate;
	private LocalDateTime modifyDate;

	public static Post from(CreatePostDto postDto, Long userId, Long locationId, Long imageId){
		return Post.builder()
			.title(postDto.getTitle())
			.content(postDto.getContent())
			.price(postDto.getPrice())
			.userId(userId)
			.locationId(locationId)
			.imageId(imageId)
			.isNegotiable(postDto.isNegotiable())
			.isOfflineTrade(postDto.isOfflineTrade())
			.createdDate(LocalDateTime.now())
			.modifyDate(LocalDateTime.now())
			.build();
	}
}
