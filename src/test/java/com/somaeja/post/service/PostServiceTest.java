package com.somaeja.post.service;

import com.somaeja.post.entity.Post;
import com.somaeja.post.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
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

	private Post post;

	@BeforeEach
	void initialPost() {
		// Given
		post = Post.builder()
			.title("title")
			.content("content")
			.price(10_000L)
			.imageId(1L)
			.createdDate(LocalDateTime.now())
			.modifyDate(LocalDateTime.now())
			.isNegotiable(false)
			.isOfflineTrade(false)
			.userId(1L)
			.locationId(1L)
			.build();
	}

	@Test
	@DisplayName("save Post Test")
	void savePost(@Mock PostMapper postMapper) {
		// When
		when(postMapper.save(post)).thenReturn(1);
		int isSave = postMapper.save(post);
		// Then
		assertThat(isSave).isGreaterThan(0);
	}
}
