package com.somaeja.user.controller;

import com.somaeja.user.dto.CreateUserDto;
import com.somaeja.user.dto.FindUserDto;
import com.somaeja.user.dto.ModifyEmailDto;
import com.somaeja.user.dto.ModifyNicknameDto;
import com.somaeja.user.dto.ModifyPasswordDto;
import com.somaeja.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/json; charset=UTF8")
public class UserController {

	private final UserService userService;

	// User 생성
	@PostMapping("/users/register")
	public ResponseEntity<String> register(@Valid @RequestBody CreateUserDto userDto) {
		userService.register(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("registration completed");
	}

	// User sign-in, sign-out 세션 값 설정 = id, role?

	// User Find
	// Login 시 사용
	@GetMapping("/users/profile")
	public ResponseEntity<FindUserDto> getUserProfile() {
		// 세션의 user Id 정보 사용 session.getAttribute("id");
		FindUserDto user = userService.findById(1L);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping("/users")
	public ResponseEntity<List<FindUserDto>> findUsersOfName(@RequestParam(value = "nickname") String nicknameOfQuery) {
		List<FindUserDto> users = userService.findByName(nicknameOfQuery);

		if (CollectionUtils.isEmpty(users)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(users);
		}
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	// User Delete

	// User Modify
	// 세션 기반 로그인 기능 생성 후에는 세션에 담긴 ID 값을 사용하도록 변경

	@PatchMapping("/users/{userId}/mail")
	public ResponseEntity<String> modifyEmail(@RequestBody String setEmail, @PathVariable Long userId) {
		userService.modifyOfEmail(new ModifyEmailDto(userId, setEmail));
		return ResponseEntity.status(HttpStatus.OK).body("modified completed");
	}

	@PatchMapping("/users/{userId}/password")
	public ResponseEntity<String> modifyPassword(@RequestBody String setPassword, @PathVariable Long userId) {
		userService.modifyOfPassword(new ModifyPasswordDto(userId, setPassword));
		return ResponseEntity.status(HttpStatus.OK).body("modified completed");
	}

	@PatchMapping("/users/{userId}/nickname")
	public ResponseEntity<String> modifyNickname(@RequestBody String setNickname, @PathVariable Long userId) {
		userService.modifyOfNickname(new ModifyNicknameDto(userId, setNickname));
		return ResponseEntity.status(HttpStatus.OK).body("modified completed");
	}

}
