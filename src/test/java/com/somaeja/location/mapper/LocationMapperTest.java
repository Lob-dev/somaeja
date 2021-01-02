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

	List<String> emptyList = new ArrayList<>();

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

	@Test
	@DisplayName("findByCity")
	void locationMapperTest_FindByCity() {
		String city = "서울";

		List<String> byCity = locationMapper.findByCity(city);
		System.out.println(byCity);
		assertNotNull(byCity);
	}

	@Test
	@DisplayName("findByCity NotFound")
	void locationMapperTest_FindByCity_NotFound() {
		String city = "런던";

		List<String> byCity = locationMapper.findByCity(city);
		System.out.println(byCity);
		assertIterableEquals(emptyList, byCity);
	}

	@Test
	@DisplayName("findByCityCountry")
	void locationMapperTest_findByCityCountry() {
		String cityCountry = "강동";

		List<String> byCityCountry = locationMapper.findByCityCountry(cityCountry);
		System.out.println(byCityCountry);
		assertNotNull(byCityCountry);
	}

	@Test
	@DisplayName("findByCityCountry NotFound")
	void locationMapperTest_findByCityCountry_NotFound() {
		String cityCountry = "동강";
		List<String> byCityCountry = locationMapper.findByCityCountry(cityCountry);
		System.out.println(byCityCountry);
		assertIterableEquals(emptyList, byCityCountry);
	}

	@Test
	@DisplayName("findByTown")
	void locationMapperTest_findByTown() {
		String town = "암사";

		List<String> byTown = locationMapper.findByTown(town);
		System.out.println(byTown);
		assertNotNull(byTown);
	}

	@Test
	@DisplayName("findByTown NotFound")
	void locationMapperTest_findByTown_NotFound() {
		String town = "사암";

		List<String> byTown = locationMapper.findByTown(town);
		System.out.println(byTown);
		assertIterableEquals(emptyList, byTown);
	}

}
