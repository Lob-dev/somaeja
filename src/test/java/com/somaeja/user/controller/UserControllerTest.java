package com.somaeja.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somaeja.user.dto.CreateUserDto;
import com.somaeja.user.dto.ModifyProfilesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	CreateUserDto userDto;

	CreateUserDto duplicateDto;

	ModifyProfilesDto profilesDto;

	@BeforeEach
	void init() {
		userDto = new CreateUserDto("서울시 강동구 암사1동",
			"babo",
			"testPassword",
			"dev@kaka.com",
			"222-2222-2222");

		duplicateDto = new CreateUserDto("서울시 강동구 암사1동",
			"babo",
			"testPassword",
			"lob@kakao.com",
			"222-2222-2222");

		profilesDto = new ModifyProfilesDto(null,
			"nickname",
			"password",
			"email");
	}


	@Test
	void userControllerTest_register() throws Exception {

		mockMvc.perform(post("/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userDto)))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	void userControllerTest_register_isDuplicated() throws Exception {

		mockMvc.perform(post("/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(duplicateDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void userControllerTest_modifyEmail() throws Exception {

		mockMvc.perform(patch("/users/profile")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(profilesDto)))
			.andDo(print())
			.andExpect(status().isOk());
	}


	@Test
	void userControllerTest_findUsersOfName() throws Exception {

		mockMvc.perform(get("/users?nickname=lob"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void userControllerTest_findUsersOfName_notFounds() throws Exception {

		mockMvc.perform(get("/users?nickname=11213asdasdffwqef"))
			.andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	void userControllerTest_getUserProfile() throws Exception {

		mockMvc.perform(get("/users/profile"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Disabled
	void userControllerTest_getUserProfile_notFounds() throws Exception {

		mockMvc.perform(get("/users/profile"))
			.andDo(print())
			.andExpect(status().isNotFound());
	}

}
