package com.somaeja.location.controller;

import org.junit.jupiter.api.DisplayName;
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
class LocationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("find locations by query")
	void findLocations() throws Exception {

		// Then
		mockMvc.perform(get("/locations?location=서울")
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("find locations by query - 키워드에 맞는 데이터가 없는 경우")
	void findLocations_NoContent() throws Exception {

		// Then
		mockMvc.perform(get("/locations?location=AAA")
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNoContent());
	}
}
