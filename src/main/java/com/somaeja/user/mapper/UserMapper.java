package com.somaeja.user.mapper;

import com.somaeja.user.dto.ModifyEmailDto;
import com.somaeja.user.dto.ModifyNicknameDto;
import com.somaeja.user.dto.ModifyPasswordDto;
import com.somaeja.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

	int register(User user);

	List<User> findByAll();

	// Profile
	User findById(Long userId);

	// 계정 찾는 용도
	User findByEmail(String searchEmail);

	List<User> findByName(String searchName);

	int modifyOfEmail(ModifyEmailDto mailDto);

	int modifyOfPassword(ModifyPasswordDto passwordDto);

	int modifyOfNickname(ModifyNicknameDto nicknameDto);

	boolean isDuplicateUserInfo(String email);
}
