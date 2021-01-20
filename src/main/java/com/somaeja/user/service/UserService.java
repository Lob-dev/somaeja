package com.somaeja.user.service;

import com.somaeja.location.mapper.LocationMapper;
import com.somaeja.user.dto.CreateUserDto;
import com.somaeja.user.dto.FindUserDto;
import com.somaeja.user.dto.ModifyProfilesDto;
import com.somaeja.user.entity.User;
import com.somaeja.user.exception.ModifyUserFailedException;
import com.somaeja.user.exception.SaveUserFailedException;
import com.somaeja.user.exception.UserInfoDuplicatedException;
import com.somaeja.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

	private final LocationMapper locationMapper;
	private final UserMapper userMapper;

	// User Create
	@Transactional
	public void register(CreateUserDto userDto) {

		Long locationId = locationMapper.findLocationId(userDto.getLocation());

		// image 정보 추가 필요

		User saveUserInfo = userDto.toEntity(locationId);

		if (userMapper.isDuplicateUserInfo(userDto.getEmail())) {
			log.info("user info duplicated : The error may be caused by same data of Email");

			throw new UserInfoDuplicatedException("User Info is Duplicated :" + userDto.getEmail());
		}

		int result = userMapper.register(saveUserInfo);
		if (isNotReflected(result)) {
			log.info("user save failed : The error may be caused by a internal server error ");

			throw new SaveUserFailedException("User Save Failed");
		}
	}

	// User Find
	@Transactional(readOnly = true)
	public FindUserDto findById(Long userId) {
		return FindUserDto.of(userMapper.findById(userId));
	}

	@Transactional(readOnly = true)
	public FindUserDto findByEmail(String email) {
		return FindUserDto.of(userMapper.findByEmail(email));
	}

	@Transactional(readOnly = true)
	public List<FindUserDto> findByName(String searchName) {
		return toDtoList(userMapper.findByName(searchName));
	}

	@Transactional(readOnly = true)
	public List<FindUserDto> findByAll() {
		return toDtoList(userMapper.findByAll());
	}

	// User Delete


	// User Modify
	public void modifyOfProfiles(ModifyProfilesDto profilesDto) {

		if (isNotReflected(userMapper.modifyOfProfiles(profilesDto))) {
			log.info("user modify failed by nickname : user id = {}", profilesDto.getId());

			throw new ModifyUserFailedException(" user modify failed by profiles : user id = " + profilesDto.getId());
		}
	}

	private List<FindUserDto> toDtoList(List<User> users) {
		return users.stream()
			.map(FindUserDto::of)
			.collect(Collectors.toList());
	}
	private boolean isNotReflected(int result) {
		return result < 1;
	}
}
