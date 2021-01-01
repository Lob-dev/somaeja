package com.somaeja.location.service;

import com.somaeja.location.mapper.LocationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

	@Mock
	LocationService locationService;

	@Mock
	LocationMapper locationMapper;

	@Test
	@DisplayName("get Location List")
	void locationServiceTest_getLocationList() {

	}
}
