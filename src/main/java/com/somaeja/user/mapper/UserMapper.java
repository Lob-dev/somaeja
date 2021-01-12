package com.somaeja.user.mapper;

import com.somaeja.user.dto.ModifyMailDto;
import com.somaeja.user.dto.ModifyNicknameDto;
import com.somaeja.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

	int save(User user);

	List<User> findByAll();

	User findById(Long userId);

	User findByEmail(String searchEmail);

	List<User> findByName(String searchName);

	int modifyOfEmail(ModifyMailDto mailDto);

	int modifyOfPassword(Map<String, String> params);

	int modifyOfNickname(ModifyNicknameDto nicknameDto);
}
