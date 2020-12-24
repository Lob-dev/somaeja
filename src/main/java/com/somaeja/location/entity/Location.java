package com.somaeja.location.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Location {
	// Insert Batch 용도
	private Long id;
	private String city;
	private String cityCountry;
	private String town;
	private String cityCountryTown;
}
