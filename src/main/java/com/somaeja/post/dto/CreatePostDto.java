package com.somaeja.post.dto;

import com.somaeja.post.entity.OfferStatus;
import com.somaeja.post.entity.TradeStatus;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter()
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {

	private String title;
	private String content;
	private Long price;

	private Long userId;
	private Long locationId;
	private Long imageId;

	private OfferStatus offerStatus;
	private TradeStatus tradeStatus;

	private LocalDateTime createdDate;
	private LocalDateTime modifyDate;

}
