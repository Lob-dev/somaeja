package com.somaeja.post.entity;

import com.somaeja.location.entity.Location;
import com.somaeja.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PostTest {

	private List<String> images;

	@Mock
	private Location location;

	@Mock
	private User user;

	@BeforeEach
	void initialPost(){
		// Given
		images = new ArrayList<>();
			images.add("image1");
			images.add("image2");
			images.add("image3");
	}

	@Test
	@DisplayName("Post Constructor Test")
	void createConstructorAndPost(){
		// Given
		Post post = Post.builder().build();

		// Then
		assertNotNull(post);
	}


	@Test
	@DisplayName("Post Create Test")
	void createPost(){
		// Given
		LocalDateTime createTime = LocalDateTime.now();

		Post post = Post.builder()
			.id(1)
			.title("title")
			.content("content")
			.price(10_000)
			.images(images)
			.createdDate(createTime)
			.modifyDate(createTime)
			.offerStatus(OfferStatus.YES)
			.tradeStatus(TradeStatus.OFFLINE)
			.build();

		// Then
		assertAll(
			() -> assertEquals(1, post.getId()),
			() -> assertEquals("title", post.getTitle()),
			() -> assertEquals("content", post.getContent()),
			() -> assertEquals(10_000, post.getPrice()),
			() -> assertEquals(images, post.getImages()),
			() -> assertEquals(createTime, post.getCreatedDate()),
			() -> assertEquals(createTime, post.getModifyDate()),
			() -> assertEquals(OfferStatus.YES, post.getOfferStatus()),
			() -> assertEquals(TradeStatus.OFFLINE, post.getTradeStatus())
		);
	}

	@Test
	@DisplayName("Post Create Time Test")
	void createPostAtTime(){
		// Then
		assertTimeout(Duration.ofMillis(30), () -> {
			Post create = Post.builder()
				.id(1)
				.title("title")
				.content("content")
				.price(10_000)
				.images(images)
				.createdDate(LocalDateTime.now())
				.modifyDate(LocalDateTime.now())
				.offerStatus(OfferStatus.YES)
				.tradeStatus(TradeStatus.OFFLINE)
				.build();
		});
	}

}
