package com.somaeja.user.mapper;

import com.somaeja.user.dto.ModifyEmailDto;
import com.somaeja.user.dto.ModifyNicknameDto;
import com.somaeja.user.dto.ModifyPasswordDto;
import com.somaeja.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

	@Autowired
	UserMapper userMapper;

	@Test
	void userMapperTest_findById() {
		// Given
		User user = userMapper.findById(1L);

		// Then
		assertNotNull(user);
	}

	@Test
	void userMapperTest_findByEmail() {
		// Given
		User user = userMapper.findByEmail("lob@kakao.com");

		// Then
		assertNotNull(user);
	}

	@Test
	void userMapperTest_findByName() {
		// Given
		List<User> users = userMapper.findByName("lob");

		// Then
		assertNotEquals(new ArrayList<User>(), users);
	}

	@Test
	void userMapperTest_findByAll() {
		// Given
		List<User> users = userMapper.findByAll();

		// Then
		assertNotEquals(new ArrayList<User>(), users);
	}

	@Test
	void userMapperTest_modifyOfEmail() {
		// Given
		ModifyEmailDto mailDto = new ModifyEmailDto(1L, "change@hello.com");

		// When
		int changed = userMapper.modifyOfEmail(mailDto);

		// Then
		assertNotEquals(0, changed);
	}

	@Test
	void userMapperTest_modifyOfPassword() {
		// Given
		ModifyPasswordDto passwordDto = new ModifyPasswordDto(1L, "testPassword");
		ModifyPasswordDto encodeDto = new ModifyPasswordDto(passwordDto.getId(), passwordDto.getEncodedPassword());

		// When
		int changed = userMapper.modifyOfPassword(encodeDto);

		// Then
		assertNotEquals(0, changed);
	}

	@Test
	void userMapperTest_modifyOfNickname() {
		// Given
		ModifyNicknameDto nicknameDto = new ModifyNicknameDto(3L, "changed");

		// When
		int changed = userMapper.modifyOfNickname(nicknameDto);

		// Then
		assertNotEquals(0, changed);
	}

	@Test
	void userMapperTest_isDuplicateUserInfo(){

		boolean result = userMapper.isDuplicateUserInfo("lob@kakao.com");

		boolean result2 = userMapper.isDuplicateUserInfo("sdasfsafasf");

		assertTrue(result);
		assertNotEquals(true, result2);
	}

}
