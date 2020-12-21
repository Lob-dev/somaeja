package com.somaeja.post.service;

import com.somaeja.post.entity.Post;
import com.somaeja.post.exception.SavePostFailedException;
import com.somaeja.post.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
class PostServiceTest {
	@Mock PostMapper postMapper;
	private Post post;
	private Post badPost;

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

		badPost = Post.builder().build();
	}

	@Test
	@DisplayName("save Post Test")
	void savePost() {
		// When
		when(postMapper.save(post)).thenReturn(1);
		int hasSaved = postMapper.save(post);

		// Then
		assertEquals(hasSaved,1);
	}

	@Test
	@DisplayName("save Post Test 실패")
	void savePost_fail(){
		// When
		when(postMapper.save(badPost)).thenThrow(new SavePostFailedException("Save Failed"));

		// Then
		assertThrows(SavePostFailedException.class, () -> postMapper.save(badPost));
	}
}
