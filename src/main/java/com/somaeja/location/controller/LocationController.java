package com.somaeja.location.controller;

import com.somaeja.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/json; charset=UTF8")
public class LocationController {

	private final LocationService locationService;

	@GetMapping(value = "/locations")
	public ResponseEntity<List<String>> findLocations(
		@RequestParam(value = "area", required = false) String areaOfQuery) {

		List<String> locations = locationService.getLocationList(areaOfQuery);
		if (CollectionUtils.isEmpty(locations)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(locations);
		}
		return ResponseEntity.status(HttpStatus.OK).body(locations);
	}
}
