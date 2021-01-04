package com.somaeja.location.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LocationMapperTest {

	@Autowired
	LocationMapper locationMapper;

	@Test
	@DisplayName("FindByLocation")
	void locationMapperTest_FindByLocation() {
		String location = "서울시 강동구 암사1동";

		assertNotNull(locationMapper.findLocationId(location));
	}

	@Test
	@DisplayName("FindByLocation NotFound")
	void locationMapperTest_FindByLocation_NotFound() {
		String location = "울서시 동강구 사암동";
		Long locationId = locationMapper.findLocationId(location);

		assertNull(locationId);
	}

}
