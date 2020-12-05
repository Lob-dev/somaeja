package com.somaeja.post.controller;

import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<String> createPost(@Valid @RequestBody CreatePostDto postDto) {
		// Image files -> Stream -> resources / static / (여기에 저장)

		int isSave = postService.savePost(postDto);

		if (isSave <= 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("false");
		}
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

}
