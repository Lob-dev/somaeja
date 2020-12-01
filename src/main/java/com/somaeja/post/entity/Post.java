package com.somaeja.post.entity;

import com.somaeja.location.entity.Location;
import com.somaeja.user.entity.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder @Getter
public class Post {

	private long id;
	private String title;
	private String content;
	private long price;

	private Location location;
	private User user;

	// YES, NO
	private OfferStatus offerStatus;

	// DELIVERY, OFFLINE
	private TradeStatus tradeStatus;

	private List<String> images;

	private LocalDateTime createdDate;
	private LocalDateTime modifyDate;
}
