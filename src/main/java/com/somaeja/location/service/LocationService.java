package com.somaeja.location.service;

import com.somaeja.location.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationMapper locationMapper;

	@Transactional(readOnly = true)
	public List<String> getLocationList(String searchArea) {
		return locationMapper.findByLocations(searchArea);
	}

}
