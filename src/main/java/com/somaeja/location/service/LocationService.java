package com.somaeja.location.service;

import com.somaeja.location.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationMapper locationMapper;

	public List<String> getLocationList(String searchKeyword) {
		return branchExecutor(searchKeyword.length(), searchKeyword);
	}

	public List<String> branchExecutor(int numCount, String searchKeyword) {
		switch (numCount) {
			case 1:
				return locationMapper.findByTown(searchKeyword);
			case 2:
				return locationMapper.findByCity(searchKeyword);
			case 3:
				return locationMapper.findByCityCountry(searchKeyword);
		}
		return new ArrayList<>();
	}

}
