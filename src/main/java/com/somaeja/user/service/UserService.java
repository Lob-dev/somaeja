package com.somaeja.user.service;

import com.somaeja.location.mapper.LocationMapper;
import com.somaeja.user.dto.CreateUserDto;
import com.somaeja.user.dto.FindUserDto;
import com.somaeja.user.dto.ModifyProfilesDto;
import com.somaeja.user.entity.User;
import com.somaeja.user.exception.DeleteUserFailedException;
import com.somaeja.user.exception.ModifyUserFailedException;
import com.somaeja.user.exception.SaveUserFailedException;
import com.somaeja.user.exception.UserInfoDuplicatedException;
import com.somaeja.user.exception.UserInfoNotFoundException;
import com.somaeja.user.exception.SaveUserRestoreInfoFailedException;
import com.somaeja.user.mapper.UserHistoryMapper;
import com.somaeja.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final LocationMapper locationMapper;
	private final UserMapper userMapper;
	private final UserHistoryMapper userHistoryMapper;

	@Transactional
	public void signUp(CreateUserDto userDto) {

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

	@Transactional
	public void deleteByUser(Long userId) {

		User user = userMapper.findById(userId);
		if (ObjectUtils.isEmpty(user)) {
			log.info("user information find failed : user id = {}", userId);

			throw new UserInfoNotFoundException("user information find failed : user id = " + userId);
		}

		if (isNotReflected(userHistoryMapper.transferUserInfo(user))) {
			log.info("user information transfer failed : user id = {}", userId);

			throw new SaveUserRestoreInfoFailedException("user information transfer failed : user id = " + userId);
		}

		if (isNotReflected(userHistoryMapper.deleteByUser(userId))) {
			log.info("user delete failed : user id = {}", userId);

			throw new DeleteUserFailedException("user delete failed : user id = " + userId);
		}
	}

	@Transactional
	public void restoreOfUser(String email) {

		User user = userHistoryMapper.findByRestoreInfo(email);
		if (ObjectUtils.isEmpty(user)) {
			log.info("user restore find failed");

			throw new UserInfoNotFoundException("user restore information failed");
		}

		if (isNotReflected(userHistoryMapper.restoreUserInfo(user))) {
			log.info("restore info find failed");

			throw new SaveUserFailedException("user restore info find failed : user id = " + user.getId());
		}

		if (isNotReflected(userHistoryMapper.deleteRestoreInfo(user.getId()))) {
			log.info("restore info delete failed");

			throw new DeleteUserFailedException("user history info delete failed");
		}
	}

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
