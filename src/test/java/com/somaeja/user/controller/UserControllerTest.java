package com.somaeja.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somaeja.config.jwt.JwtFilter;
import com.somaeja.user.dto.CreateUserDto;
import com.somaeja.user.dto.ModifyProfilesDto;
import com.somaeja.user.dto.SignInUserDto;
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

@AutoConfigureMockMvc(addFilters = false)
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
			.header(JwtFilter.AUTHORIZATION_HEADER, "eyJhbGciOiJIUzUxMiJ9." +
				"eyJzdWIiOiIxIiwiZXhwIjoxNjEzNzQyODUzMjczfQ." +
				"7kf4YpTo6TorkHSMVEKYz_jKKll1e7HR4CllHvtZXFxZ2ygIvOgDE13Mj_jYhyqma-kVvd6-ELJ-TZrVWqofcQ")
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
			.header(JwtFilter.AUTHORIZATION_HEADER, "eyJhbGciOiJIUzUxMiJ9." +
				"eyJzdWIiOiIxIiwiZXhwIjoxNjEzNzQyODUzMjczfQ." +
				"7kf4YpTo6TorkHSMVEKYz_jKKll1e7HR4CllHvtZXFxZ2ygIvOgDE13Mj_jYhyqma-kVvd6-ELJ-TZrVWqofcQ"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void userControllerTest_softDeleteOfUser() throws Exception {

		mockMvc.perform(delete("/users/{userId}", 3L)
			.header(JwtFilter.AUTHORIZATION_HEADER, "eyJhbGciOiJIUzUxMiJ9." +
					"eyJzdWIiOiIzIiwiZXhwIjoxNjEzNzQyNDQ1MTU3fQ." +
					"obNq1IlriYcqpMeytrV5oX4nqTxeb4rDXxz0_r0zYbJagC-Kjkxluer7KhfF_hRS36hE95cAe5ptiecOdl_lqQ"))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	void userControllerTest_softDeleteOfUser_notFounds() throws Exception {

		mockMvc.perform(delete("/users/{userId}", 24124124124L)
			.header(JwtFilter.AUTHORIZATION_HEADER, "eyJhbGciOiJIUzUxMiJ9." +
					"eyJzdWIiOiIxIiwiZXhwIjoxNjEzNzQyODUzMjczfQ." +
					"7kf4YpTo6TorkHSMVEKYz_jKKll1e7HR4CllHvtZXFxZ2ygIvOgDE13Mj_jYhyqma-kVvd6-ELJ-TZrVWqofcQ"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void userControllerTest_restoreOfUser() throws Exception {

		mockMvc.perform(delete("/users/{userId}", 4L)
			.header(JwtFilter.AUTHORIZATION_HEADER, "eyJhbGciOiJIUzUxMiJ9." +
					"eyJzdWIiOiI0IiwiZXhwIjoxNjEzNzQzMTYyMjk1fQ." +
					"z_8G_n83ZP1yg2OWEleu5eKNwkgrRGw9uQnUrKiSHaFbPQMeJjr_2TyApdXY6Gx6wkIpkjP4ZLOhE4WFV_h2Aw"))
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

		mockMvc.perform(get("/users/profile")
			.header(JwtFilter.AUTHORIZATION_HEADER, "Bearer eyJhbGciOiJIUzUxMiJ9" +
				".eyJzdWIiOiIzIiwiaWF0IjoxNjEzNzkzMDk3LCJleHAiOjE2MTM4Nzk0OTcwMTl9" +
				".mf_X4pezsykgqVW2FlzlqgNsvydZ5w6KzyAsUPj8hraf_QOMB0cPB_5ygVHLZmlG2nal0Cgn1LuO5-r_1zh7Uw"))
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
	@Disabled
	void userControllerTest_signOut() throws Exception {

		mockMvc.perform(get("/users/sign-out"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
