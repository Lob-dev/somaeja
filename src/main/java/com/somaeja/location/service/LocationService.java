package com.somaeja.location.service;

import com.somaeja.location.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationMapper locationMapper;

	public List<String> getLocationList(String searchKeyword) {
		return locationMapper.findByLocations(searchKeyword);
	}

}
