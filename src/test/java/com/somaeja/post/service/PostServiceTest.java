package com.somaeja.post.service;

import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.entity.OfferStatus;
import com.somaeja.post.entity.TradeStatus;
import com.somaeja.post.mapper.PostMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class PostServiceTest {

	@Test
	@DisplayName("save Post Test")
	void savePost(@Mock PostMapper postMapper){

		// Given
		CreatePostDto postDto = CreatePostDto.builder()
			.title("title")
			.content("content")
			.price(10_000L)
			.imageId(1L)
			.createdDate(LocalDateTime.now())
			.modifyDate(LocalDateTime.now())
			.offerStatus(OfferStatus.YES)
			.tradeStatus(TradeStatus.OFFLINE)
			.userId(1L)
			.locationId(1L)
			.build();

		// When
		when(postMapper.save(postDto)).thenReturn(1);
		int isSave = postMapper.save(postDto);

		// Then
		assertThat(isSave).isGreaterThan(0);
	}
}
