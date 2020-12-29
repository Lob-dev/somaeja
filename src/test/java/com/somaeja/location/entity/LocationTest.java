package com.somaeja.location.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

	@Test
	void LocationTest_Builder() {
		// Given
		Location location = Location.builder().build();
		// Then
		assertNotNull(location);
	}

	@Test
	void LocationTest_Create() {
		// Given
		Location location = Location.builder()
			.id(1L)
			.city("서울시")
			.cityCountry("강동구")
			.town("암사1동")
			.cityCountryTown("서울시 강동구 암사1동")
			.build();

		// Then
		assertAll(
			() -> assertEquals(1L , location.getId()),
			() -> assertEquals("서울시", location.getCity()),
			() -> assertEquals("강동구", location.getCityCountry()),
			() -> assertEquals("암사1동", location.getTown())
		);
	}

}
