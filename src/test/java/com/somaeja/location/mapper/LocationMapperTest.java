package com.somaeja.location.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
public class LocationMapperTest {

	@Autowired
	LocationMapper locationMapper;

	List<String> emptyList = new ArrayList<>();

	@Test
	@DisplayName("FindByLocation")
	void LocationMapperTest_FindByLocation() {
		String location = "서울시 강동구 암사1동";

		assertEquals(1L, locationMapper.findLocationId(location));
	}

	@Test
	@DisplayName("FindByLocation NotFound")
	void LocationMapperTest_FindByLocation_NotFound() {
		String location = "울서시 동강구 사암동";
		Long locationId = locationMapper.findLocationId(location);

		assertEquals(null, locationId);
	}

	@Test
	@DisplayName("findByCity")
	void LocationMapperTest_FindByCity() {
		String city = "서울";

		List<String> byCity = locationMapper.findByCity(city);
		System.out.println(byCity.toString());
		assertNotEquals(null, byCity);
	}

	@Test
	@DisplayName("findByCity NotFound")
	void LocationMapperTest_FindByCity_NotFound() {
		String city = "런던";

		List<String> byCity = locationMapper.findByCity(city);
		System.out.println(byCity.toString());
		assertEquals(emptyList, byCity);
	}

	@Test
	@DisplayName("findByCityCountry")
	void LocationMapperTest_findByCityCountry() {
		String cityCountry = "강동";

		List<String> byCityCountry = locationMapper.findByCityCountry(cityCountry);
		System.out.println(byCityCountry.toString());
		assertNotEquals(null, byCityCountry);
	}

	@Test
	@DisplayName("findByCityCountry NotFound")
	void LocationMapperTest_findByCityCountry_NotFound() {
		String cityCountry = "동강";
		List<String> byCityCountry = locationMapper.findByCityCountry(cityCountry);
		System.out.println(byCityCountry.toString());
		assertEquals(emptyList, byCityCountry);
	}

	@Test
	@DisplayName("findByTown")
	void LocationMapperTest_findByTown() {
		String town = "암사";

		List<String> byTown = locationMapper.findByTown(town);
		System.out.println(byTown.toString());
		assertNotEquals(null, byTown);
	}

	@Test
	@DisplayName("findByTown NotFound")
	void LocationMapperTest_findByTown_NotFound() {
		String town = "사암";

		List<String> byTown = locationMapper.findByTown(town);
		System.out.println(byTown.toString());
		assertEquals(emptyList, byTown);
	}

}
