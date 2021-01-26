package com.somaeja.user.mapper;

import com.somaeja.user.dto.ModifyProfilesDto;
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
	User findByEmail(String email);

	List<User> findByName(String nickName);

	int modifyOfProfiles(ModifyProfilesDto profilesDto);

	boolean isDuplicateUserInfo(String email);

	// User Soft Delete , Restore
	int transferUserInfo(User user);

	int restoreUserInfo(User user);

	User findByRestoreInfo(String email);

	int deleteRestoreInfo(Long userId);

	int deleteByUser(Long userId);

}
