package com.somaeja.post.entity;

import com.somaeja.post.dto.CreatePostDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
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

}
