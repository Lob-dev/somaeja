package com.somaeja.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PACKAGE)
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

	// YES, NO
	private OfferStatus offerStatus;
	// DELIVERY, OFFLINE
	private TradeStatus tradeStatus;

	private LocalDateTime createdDate;
	private LocalDateTime modifyDate;
}
