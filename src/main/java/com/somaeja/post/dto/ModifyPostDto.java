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

	public Post toUpdateEntity(Long postId, Long locationId, Long imageId, Post savedEntity) {

		executeVerificationOfDto(savedEntity);

		return Post.builder()
			.id(postId)
			.title(title)
			.content(content)
			.price(price)
			.userId(userId)
			.locationId(locationId)
			.imageId(imageId)
			.isNegotiable(isNegotiable)
			.isOfflineTrade(isOfflineTrade)
			.createdDate(LocalDateTime.now())
			.modifyDate(LocalDateTime.now())
			.build();
	}

	private void executeVerificationOfDto(Post savedEntity) {
		if (!savedEntity.isSameTitle(title) && title.equals("")) {
			title = savedEntity.getTitle();
		} else if (!savedEntity.isSameContent(content) && content.equals("")) {
			content = savedEntity.getContent();
		} else if (!savedEntity.isSamePrice(price) && price.equals(0L)) {
			price = savedEntity.getPrice();
		} else if (!savedEntity.isSameUserId(userId) && userId.equals(0L)) {
			userId = savedEntity.getUserId();
		}
	}
}
