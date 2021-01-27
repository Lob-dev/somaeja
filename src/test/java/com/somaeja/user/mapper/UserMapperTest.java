package com.somaeja.user.mapper;

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

	@Autowired
	UserHistoryMapper userHistoryMapper;

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
	void userMapperTest_isDuplicateUserInfo() {

		boolean result = userMapper.isDuplicateUserInfo("lob@kakao.com");

		boolean result2 = userMapper.isDuplicateUserInfo("sdasfsafasf");

		assertTrue(result);
		assertNotEquals(true, result2);
	}

	@Test
	void userMapperTest_userDeleteByUser() {

		Long userId = 2L;
		User user = userMapper.findById(userId);

		int res = userHistoryMapper.transferUserInfo(user);
		res = userHistoryMapper.deleteByUser(userId);

		User result = userHistoryMapper.findByRestoreInfo("test@kakao.com");

		System.out.println(user);
		System.out.println(result);
	}
}
