package com.somaeja.user.service;

import com.somaeja.config.jwt.JwtTokenProvider;
import com.somaeja.user.dto.SignInUserDto;
import com.somaeja.user.dto.UserInfo;
import com.somaeja.user.exception.UserInfoNotFoundException;
import com.somaeja.user.mapper.UserMapper;
import com.somaeja.user.utils.SHA256Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService implements UserDetailsService {

	private final UserMapper userMapper;
	private final JwtTokenProvider tokenProvider;

	public String signIn(SignInUserDto userDto) {

		UserInfo info = userMapper.findByEmailAndPassword(userDto.getEmail(),
			"{noop}" + SHA256Utils.encode(userDto.getPassword()));

		if (ObjectUtils.isEmpty(info)) {
			log.info("user information find failed");

			throw new UserInfoNotFoundException("user information find failed");
		}
		return tokenProvider.createToken(info.getId());
	}


	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		com.somaeja.user.entity.User user = userMapper.findById(Long.valueOf(userId));
		if (ObjectUtils.isEmpty(user)) {
			log.info("User Info Not Found : User information could not be found in the database. : User userId = {}", userId);
			throw new UserInfoNotFoundException("User Info Not Found :" + userId);
		}
		return createUser(userId, user.getRole().toString());
	}

	private User createUser(String userId, String role) {

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority(role)));

		return new User(userId, "", grantedAuthorities);
	}

}
