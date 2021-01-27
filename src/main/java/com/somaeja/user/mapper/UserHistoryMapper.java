package com.somaeja.user.mapper;

import com.somaeja.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryMapper {

	int transferUserInfo(User user);

	int restoreUserInfo(User user);

	User findByRestoreInfo(String email);

	int deleteRestoreInfo(Long userId);

	int deleteByUser(Long userId);

}
