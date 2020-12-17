package com.somaeja.post.entity;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Getter
public class Post {

	private Long id;
	// User 정보
	private Long userId;
	// Location 정보
	private Long locationId;
	// Image Path 정보
	private Long imageId;
	private String title;
	private String content;
	private Long price;
	private boolean isNegotiable;
	private boolean isOfflineTrade;
	private LocalDateTime createdDate;
	private LocalDateTime modifyDate;


	public boolean isSameTitle(String title) {
		return Objects.equals(this.title, title);
	}

	public boolean isSameContent(String content) {
		return Objects.equals(this.content, content);
	}

	public boolean isSamePrice(Long price) {
		return Objects.equals(this.price, price);
	}

	public boolean isSameUserId(Long userId) {
		return Objects.equals(this.userId, userId);
	}

	public boolean isSameLocationId(Long locationId) {
		return Objects.equals(this.locationId, locationId);
	}

	public boolean isSameImageId(Long imageId) {
		return Objects.equals(this.imageId, imageId);
	}

	public boolean isSameNegotiable(boolean isNegotiable) {
		return Objects.equals(this.isNegotiable, isNegotiable);
	}

	public boolean isSameTrade(boolean isOfflineTrade) {
		return Objects.equals(this.isOfflineTrade, isOfflineTrade);
	}
}
