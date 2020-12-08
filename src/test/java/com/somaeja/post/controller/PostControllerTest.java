package com.somaeja.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	CreatePostDto postDto;

	@BeforeEach
	void initPost() {
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

		mockMvc.perform(post("/posts")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(postDto)))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("find post By all")
	void findPostByAll() throws Exception {

		mockMvc.perform(get("/posts"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find post By title")
	void findPostByTitle() throws Exception {

		mockMvc.perform(get("/posts/titles/{title}", "title"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find post By location")
	void findPostByLocation() throws Exception {

		mockMvc.perform(get("/posts/locations/{location}", "location"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find post By user")
	void findPostByUser() throws Exception {

		mockMvc.perform(get("/posts/users/{user}", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
