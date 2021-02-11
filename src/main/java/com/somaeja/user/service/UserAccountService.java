package com.somaeja.user.service;

import com.somaeja.user.dto.SignInUserDto;
import com.somaeja.user.dto.UserInfo;
import com.somaeja.user.exception.UserInfoNotFoundException;
import com.somaeja.user.mapper.UserMapper;
import com.somaeja.user.utils.SHA256Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {

	private final UserMapper userMapper;

	public void signIn(SignInUserDto userDto, HttpSession session) {

		UserInfo user = userMapper.findByEmailAndPassword(userDto.getEmail(), SHA256Utils.encode(userDto.getEmail()));
		if (ObjectUtils.isEmpty(user)) {
			log.info("user information find failed");

			throw new UserInfoNotFoundException("user information find failed");
		}

		setupSession(session, user);
	}

	public void signOut(HttpSession session) {
		session.invalidate();
	}

	private void setupSession(HttpSession session, UserInfo user) {
		session.setAttribute("ID", user.getId());
		session.setAttribute("ROLE", user.getRole());
		session.setMaxInactiveInterval(5 * 60);
	}

}
