package com.somaeja.post.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class PostTest {

	@Test
	@DisplayName("Post Constructor Test")
	void createConstructorAndPost() {
		// Given
		Post post = Post.builder().build();
		// Then
		assertNotNull(post);
	}


	@Test
	@DisplayName("Post Create Test")
	void createPost() {
		// Given
		LocalDateTime createTime = LocalDateTime.now();

		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.price(10_000L)
			.createdDate(createTime.toString())
			.modifyDate(createTime.toString())
			.isNegotiable(false)
			.isOfflineTrade(false)
			.userId(1L)
			.locationId(1L)
			.build();

		// Then
		assertAll(
			() -> assertEquals(Long.valueOf(1), post.getId()),
			() -> assertEquals("title", post.getTitle()),
			() -> assertEquals("content", post.getContent()),
			() -> assertEquals(Long.valueOf(10_000), post.getPrice()),
			() -> assertEquals(createTime.toString(), post.getCreatedDate()),
			() -> assertEquals(createTime.toString(), post.getModifyDate()),
			() -> assertFalse(post.isNegotiable()),
			() -> assertFalse(post.isOfflineTrade()),
			() -> assertEquals(Long.valueOf(1), post.getUserId()),
			() -> assertEquals(Long.valueOf(1), post.getLocationId())
		);
	}

	@Test
	@DisplayName("Post Create Time Test")
	void createPostAtTime() {
		// Then
		assertTimeout(Duration.ofMillis(30), () -> {
			Post create = Post.builder()
				.id(1L)
				.title("title")
				.content("content")
				.price(10_000L)
				.createdDate(LocalDateTime.now().toString())
				.modifyDate(LocalDateTime.now().toString())
				.isNegotiable(false)
				.isOfflineTrade(false)
				.userId(1L)
				.locationId(1L)
				.build();
		});
	}

}
