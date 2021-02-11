package com.somaeja.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somaeja.user.dto.CreateUserDto;
import com.somaeja.user.dto.ModifyProfilesDto;
import com.somaeja.user.dto.SignInUserDto;
import org.junit.jupiter.api.BeforeEach;
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

	SignInUserDto signInUserDto;

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

		signInUserDto = new SignInUserDto(
			"hello@kakao.com",
			"passwords");

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
			.sessionAttr("ID", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(profilesDto)))
			.andDo(print())
			.andExpect(status().isOk());
	}


	@Test
	void userControllerTest_findUsersOfName() throws Exception {

		mockMvc.perform(get("/users?nickname=test"))
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

		mockMvc.perform(get("/users/profile")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void userControllerTest_softDeleteOfUser() throws Exception {

		mockMvc.perform(delete("/users/{userId}", 3L)
			.sessionAttr("ID", 3L))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	void userControllerTest_softDeleteOfUser_notFounds() throws Exception {

		mockMvc.perform(delete("/users/{userId}", 24124124124L)
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void userControllerTest_restoreOfUser() throws Exception {

		mockMvc.perform(delete("/users/{userId}", 4L)
			.sessionAttr("ID", 4L))
			.andDo(print())
			.andExpect(status().isNoContent());

		mockMvc.perform(get("/users/restore?email=hellos@kakao.com"))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	void userControllerTest_restoreOfUser_notFounds() throws Exception {

		mockMvc.perform(get("/users/restore?email=lob@kakao.com"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void userControllerTest_signIn() throws Exception {

		mockMvc.perform(post("/users/sign-in")
			.content(objectMapper.writeValueAsString(signInUserDto))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void userControllerTest_signIn_notValid() throws Exception {

		SignInUserDto userDto = new SignInUserDto();

		mockMvc.perform(post("/users/sign-in")
			.content(objectMapper.writeValueAsString(userDto))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void userControllerTest_signIn_notFound() throws Exception {

		SignInUserDto userDto = new SignInUserDto("123", "456");

		mockMvc.perform(post("/users/sign-in")
			.content(objectMapper.writeValueAsString(userDto))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void userControllerTest_signOut() throws Exception {

		mockMvc.perform(get("/users/sign-out"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
