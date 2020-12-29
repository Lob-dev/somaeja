package com.somaeja.location.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Location {
	// Insert Batch 용도
	private final Long id;
	private final String city;
	private final String cityCountry;
	private final String town;
	private final String cityCountryTown;
}
