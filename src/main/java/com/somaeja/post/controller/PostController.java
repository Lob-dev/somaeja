package com.somaeja.post.controller;

import com.somaeja.config.jwt.JwtFilter;
import com.somaeja.config.jwt.JwtTokenProvider;
import com.somaeja.post.controller.response.PostInfo;
import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.dto.FindPostDto;
import com.somaeja.post.dto.ModifyPostDto;
import com.somaeja.post.entity.Post;
import com.somaeja.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/json; charset=UTF8")
public class PostController {

	private final PostService postService;
	private final JwtTokenProvider tokenProvider;

	@PostMapping("/posts")
	public ResponseEntity<PostInfo> createPostInfo(@Valid @RequestBody CreatePostDto postDto,
												   @RequestHeader(JwtFilter.AUTHORIZATION_HEADER) String jwt) {
		// Image files -> Stream -> resources / static / (여기에 저장)
		Long userId = Long.valueOf(tokenProvider.getUserId(jwt));
		Post post = postService.savePostInfo(postDto, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(PostInfo.from(post.getId(), "post created!"));
	}

	@GetMapping(value = "/posts")
	public ResponseEntity<List<FindPostDto>> findPostAll(@RequestParam(value = "title", required = false) String titleOfQuery,
														 @RequestParam(value = "content", required = false) String contentOfQuery) {

		if (titleOfQuery == null && contentOfQuery == null) {
			List<FindPostDto> posts = postService.findByAll();
			if (CollectionUtils.isEmpty(posts)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(posts);
			}

			return ResponseEntity.status(HttpStatus.OK).body(posts);
		} else if (contentOfQuery != null) {
			List<FindPostDto> posts = postService.findByContent(contentOfQuery);
			if (CollectionUtils.isEmpty(posts)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(posts);
			}

			return ResponseEntity.status(HttpStatus.OK).body(posts);
		} else {
			List<FindPostDto> posts = postService.findByTitle(titleOfQuery);
			if (CollectionUtils.isEmpty(posts)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(posts);
			}

			return ResponseEntity.status(HttpStatus.OK).body(posts);
		}
	}

	@GetMapping(value = "/locations/{locationId}/posts")
	public ResponseEntity<List<FindPostDto>> findPostByLocation(@PathVariable Long locationId) {
		List<FindPostDto> posts = postService.findByLocation(locationId);
		if (CollectionUtils.isEmpty(posts)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(posts);
		}
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}

	@GetMapping(value = "/users/{userId}/posts")
	public ResponseEntity<List<FindPostDto>> findPostByUser(@PathVariable Long userId) {
		List<FindPostDto> posts = postService.findByUser(userId);
		if (CollectionUtils.isEmpty(posts)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(posts);
		}
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<PostInfo> deletePostInfo(@PathVariable Long postId,
												   @RequestHeader(JwtFilter.AUTHORIZATION_HEADER) String jwt) {
		Long userId = Long.valueOf(tokenProvider.getUserId(jwt));
		postService.deletePostInfo(postId, userId);
		return ResponseEntity.status(HttpStatus.OK).body(PostInfo.from(postId, "post deleted!"));
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostInfo> changePostInfo(@PathVariable Long postId, @Valid @RequestBody ModifyPostDto modifyPostDto,
												   @RequestHeader(JwtFilter.AUTHORIZATION_HEADER) String jwt) {
		Long userId = Long.valueOf(tokenProvider.getUserId(jwt));
		Post post = postService.changePostInfo(postId, userId, modifyPostDto);
		return ResponseEntity.status(HttpStatus.OK).body(PostInfo.from(post.getId(), "post updated!"));
	}

}
