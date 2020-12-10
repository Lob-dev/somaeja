package com.somaeja.post.controller;

import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.dto.FindPostDto;
import com.somaeja.post.entity.Post;
import com.somaeja.post.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/posts")
	public ResponseEntity<PostInfo> createPost(@Valid @RequestBody CreatePostDto postDto) {
		// Image files -> Stream -> resources / static / (여기에 저장)
		Post savePost = postService.savePost(postDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(PostInfo.from(savePost.getId(),"post created!"));
	}

	@GetMapping("/posts")
	public ResponseEntity<List<FindPostDto>> findPostAll(
		@RequestParam(value = "title", required = false) String title) {

		if (title == null) {
			List<FindPostDto> postsByAll = postService.findByAll();
			if (CollectionUtils.isEmpty(postsByAll)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postsByAll);
			}

			return ResponseEntity.status(HttpStatus.OK).body(postsByAll);
		} else {
			List<FindPostDto> postsByTitle = postService.findByTitle(title);
			if (CollectionUtils.isEmpty(postsByTitle)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postsByTitle);
			}

			return ResponseEntity.status(HttpStatus.OK).body(postsByTitle);
		}
	}

	@GetMapping("/posts/locations/{locationId}")
	public ResponseEntity<List<FindPostDto>> findPostByLocation(@PathVariable Long locationId) {
		List<FindPostDto> PostsByLocation = postService.findByLocation(locationId);
		if (CollectionUtils.isEmpty(PostsByLocation)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(PostsByLocation);
		}
		return ResponseEntity.status(HttpStatus.OK).body(PostsByLocation);
	}

	@GetMapping("/posts/users/{userId}")
	public ResponseEntity<List<FindPostDto>> findPostByUser(@PathVariable Long userId) {
		List<FindPostDto> PostsByUser = postService.findByUser(userId);
		if (CollectionUtils.isEmpty(PostsByUser)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(PostsByUser);
		}
		return ResponseEntity.status(HttpStatus.OK).body(PostsByUser);
	}

	@Getter
	@AllArgsConstructor
	private static class PostInfo{
		private Long postId;
		private String message;
		private static PostInfo from(Long id, String message){
			return new PostInfo(id, message);
		}
	}
}
