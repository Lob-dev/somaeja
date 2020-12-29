package com.somaeja.post.dto;

import com.somaeja.post.entity.Post;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModifyPostDto {

	@NotNull
	@Min(0)
	private Long userId;

	@NotEmpty
	private String imageName;

	@NotEmpty
	private String title;

	@NotEmpty
	private String content;

	@NotNull
	@Min(0)
	private Long price;

	@NotEmpty
	private String location;

	private boolean isNegotiable;
	private boolean isOfflineTrade;

	public Post toEntity(Long postId, Long locationId, Long imageId) {

		return Post.builder()
			.id(postId)
			.title(title)
			.content(content)
			.price(price)
			.userId(userId)
			.locationId(locationId)
			.cityCountryTown(location)
			.imageId(imageId)
			.isNegotiable(isNegotiable)
			.isOfflineTrade(isOfflineTrade)
			.createdDate(LocalDateTime.now())
			.modifyDate(LocalDateTime.now())
			.build();
	}
}
