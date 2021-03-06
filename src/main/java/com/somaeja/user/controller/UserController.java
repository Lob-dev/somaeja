package com.somaeja.user.controller;

import com.somaeja.config.jwt.JwtFilter;
import com.somaeja.config.jwt.JwtTokenProvider;
import com.somaeja.user.dto.CreateUserDto;
import com.somaeja.user.dto.FindUserDto;
import com.somaeja.user.dto.ModifyProfilesDto;
import com.somaeja.user.dto.SignInUserDto;
import com.somaeja.user.service.UserAccountService;
import com.somaeja.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/json; charset=UTF8")
public class UserController {

	private final UserService userService;
	private final UserAccountService userAccountService;
	private final JwtTokenProvider tokenProvider;

	@PostMapping("/users/register")
	public ResponseEntity<String> signUp(@Valid @RequestBody CreateUserDto userDto) {
		userService.signUp(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("registration completed");
	}

	@PostMapping("/users/sign-in")
	public ResponseEntity<String> signIn(@Valid @RequestBody SignInUserDto userDto) {
		String jwt = userAccountService.signIn(userDto);

		HttpHeaders jwtHeaders = new HttpHeaders();
		jwtHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

		return ResponseEntity.status(HttpStatus.OK).headers(jwtHeaders).body("sign-in Success");
	}

/*
	@GetMapping("/users/sign-out")
	public ResponseEntity<String> signOut(HttpSession session) {
		userAccountService.signIn(session);
		return ResponseEntity.status(HttpStatus.OK).body("sign-out Success");
	}
*/

	@GetMapping("/users/profile")
	public ResponseEntity<FindUserDto> getUserProfile(@RequestHeader(JwtFilter.AUTHORIZATION_HEADER) String jwt) {
		Long userId = Long.valueOf(tokenProvider.getUserId(jwt));
		FindUserDto user = userService.findById(userId);
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

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> softDeleteOfUser(@PathVariable Long userId,
												   @RequestHeader(JwtFilter.AUTHORIZATION_HEADER) String jwt) {

		Long requestUserId = Long.valueOf(tokenProvider.getUserId(jwt));
		if (userId.equals(requestUserId)) {
			userService.deleteByUser(userId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete completed");
		}
		log.info("user information does not match : requested ID is {}, but {} came in.", userId, requestUserId);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user information does not match");
	}

	@GetMapping("/users/restore")
	public ResponseEntity<String> restoreOfUser(@RequestParam(value = "email") String emailOfQuery) {
		userService.restoreOfUser(emailOfQuery);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("restore completed");
	}

	@PatchMapping("/users/profile")
	public ResponseEntity<String> modifyProfiles(@RequestBody ModifyProfilesDto dto,
												 @RequestHeader(JwtFilter.AUTHORIZATION_HEADER) String jwt) {
		Long userId = Long.valueOf(tokenProvider.getUserId(jwt));
		userService.modifyOfProfiles(new ModifyProfilesDto(userId, dto.getNickname(), dto.getPassword(), dto.getEmail()));
		return ResponseEntity.status(HttpStatus.OK).body("modify completed");
	}

}
