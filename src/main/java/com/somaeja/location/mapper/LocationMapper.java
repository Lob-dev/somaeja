package com.somaeja.location.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationMapper {

	Long findLocationId(String searchLocation);

	List<String> findByLocations(String searchKeyword);

}
