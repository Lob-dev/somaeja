package com.somaeja.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.dto.ModifyPostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	CreatePostDto postDto;

	CreatePostDto BadPostDto;

	@BeforeEach
	void initPost() {
		// Given
		postDto = CreatePostDto.builder()
			.title("title")
			.content("content")
			.price(1000L)
			.location("location")
			.isNegotiable(false)
			.isOfflineTrade(false)
			.build();
	}

	@Test
	@DisplayName("create post test")
	void createPost() throws Exception {
		// Then
		ResultActions resultActions = mockMvc.perform(post("/posts")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(postDto)))
			.andDo(print())
			.andExpect(status().isCreated());

		System.out.println(resultActions.andReturn()
			.getResponse()
			.getContentAsString()
		);
	}

	@Test
	@DisplayName("create post test - 실패, input 값이 없을 경우")
	void createPost_BadRequest() throws Exception {
		// Given
		BadPostDto = CreatePostDto.builder().build();

		// Then
		mockMvc.perform(post("/posts")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(BadPostDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("create post test - 실패, 비어있는 Input 값을 받을 경우")
	void createPost_BadRequest_WrongType() throws Exception{
		// Given
		BadPostDto = CreatePostDto.builder()
			.title("")
			.content("")
			.price(1000L)
			.isNegotiable(false)
			.isOfflineTrade(false)
			.location("location")
			.build();
		// Then
		mockMvc.perform(post("/posts")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(BadPostDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("find post By all")
	void findPostByAll() throws Exception {
		// Then
		mockMvc.perform(get("/posts"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find post By title")
	void findPostByTitle() throws Exception {
		// Then
		mockMvc.perform(get("/posts?title=title"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find post By title - 해당하는 컨텐츠가 없는 경우")
	void findPostByTitle_NoContent() throws Exception {
		// Then
		mockMvc.perform(get("/posts?title=absdssesea"))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("find post By location")
	void findPostByLocation() throws Exception {
		// Then
		mockMvc.perform(get("/locations/{locationId}/posts", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find post By location - 실패, 잘못된 타입의 값이 넘어왔을 경우")
	void findPostByLocation_BadRequest_WrongType() throws Exception {
		// Then
		mockMvc.perform(get("/locations/{locationId}/posts", "1L"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("find post By location - 해당하는 컨텐츠가 없는 경우")
	void findPostByLocation_NoContent() throws Exception {
		// Then
		mockMvc.perform(get("/locations/{locationId}/posts", 113214L))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("find post By user")
	void findPostByUser() throws Exception {
		// Then
		mockMvc.perform(get("/users/{userId}/posts", 3L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find post By user - 실패, 잘못된 타입의 값이 넘어왔을 경우")
	void findPostByUser_BadRequest_WrongType() throws Exception {
		// Then
		mockMvc.perform(get("/users/{userId}/posts", "1L"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("find post By user - 해당하는 컨텐츠가 없는 경우")
	void findPostByUser_NoContent() throws Exception {
		// Then
		mockMvc.perform(get("/users/{userId}/posts", 113214L))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("delete post by postId")
	void deletePostByPostId() throws Exception {
		// Then
		mockMvc.perform(delete("/posts/{postId}" , 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("delete post by postId - 실패, 잘못된 타입의 값이 넘어왔을 경우")
	void deletePostByPostId_BadRequest_WrongType() throws Exception {
		// Then
		mockMvc.perform(delete("/posts/{postId}" , "str"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("delete post by postId - 해당하는 컨텐츠가 없는 경우")
	void deletePostByPostId_NoContent() throws Exception {
		// Then
		mockMvc.perform(delete("/posts/{postId}" , 1213124415))
			.andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Modify post by postId")
	void modifyPostByPostId() throws Exception {
		// Given
		ModifyPostDto postDto = ModifyPostDto.builder()
			.userId(1L)
			.imageName("image")
			.title("title")
			.content("content")
			.price(100L)
			.location("location")
			.build();

		// Then
		mockMvc.perform(patch("/posts/{postId}" , 1L)
			.content(objectMapper.writeValueAsString(postDto))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Modify post by postId - 실패, 잘못된 타입의 값이 넘어왔을 경우")
	void modifyPostByPostId_BadRequest_WrongType() throws Exception {
		// Given
		ModifyPostDto postDto = ModifyPostDto.builder()
			.userId(1L)
			.imageName("image")
			.title("title")
			.content("content")
			.price(100L)
			.location("location")
			.build();

		// Then
		mockMvc.perform(patch("/posts/{postId}" , "str")
			.content(objectMapper.writeValueAsString(postDto))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Modify post by postId - 해당하는 컨텐츠가 없는 경우")
	void modifyPostByPostId_NoContent() throws Exception {
		// Given
		ModifyPostDto postDto = ModifyPostDto.builder()
			.userId(1L)
			.imageName("image")
			.title("title")
			.content("content")
			.price(100L)
			.location("location")
			.build();

		// Then
		mockMvc.perform(patch("/posts/{postId}", 1123241L)
			.content(objectMapper.writeValueAsString(postDto))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound());
	}
}
