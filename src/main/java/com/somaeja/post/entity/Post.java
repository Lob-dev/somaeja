package com.somaeja.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class Post {

	private final Long id;
	// User 정보
	private final Long userId;
	// Location 정보
	private final Long locationId;
	// Image Path 정보
	private final Long imageId;
	private final String title;
	private final String content;
	private final Long price;
	private final boolean isNegotiable;
	private final boolean isOfflineTrade;
	private final String createdDate;
	private final String modifyDate;
	private final String cityCountryTown;

}
