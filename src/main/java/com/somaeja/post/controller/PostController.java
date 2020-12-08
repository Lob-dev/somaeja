package com.somaeja.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.dto.FindPostDto;
import com.somaeja.post.service.PostService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/posts")
	public ResponseEntity<String> createPost(@Valid @RequestBody CreatePostDto postDto) {
		// Image files -> Stream -> resources / static / (여기에 저장)

		int isSave = postService.savePost(postDto);

		if (isSave <= 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("false");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("success");
	}

	@GetMapping("/posts")
	public ResponseEntity<Object> findPostAll() {
		List<FindPostDto> byAll = postService.findByAll();
		if (ObjectUtils.isEmpty(byAll)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(byAll);
		}
		return ResponseEntity.status(HttpStatus.OK).body(byAll);
	}

	@GetMapping("/posts/titles/{title}")
	public ResponseEntity<Object> findPostByTitle(@PathVariable String title) {
		List<FindPostDto> byTitle = postService.findByTitle(title);
		if (ObjectUtils.isEmpty(byTitle)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(byTitle);
		}
		return ResponseEntity.status(HttpStatus.OK).body(byTitle);
	}

	@GetMapping("/posts/locations/{location}")
	public ResponseEntity<Object> findPostByLocation(@PathVariable String location) {
		// long locationId = locationService.findLocation(location);
		long locationId = 1;
		List<FindPostDto> byLocation = postService.findByLocation(locationId);
		if (ObjectUtils.isEmpty(byLocation)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(byLocation);
		}
		return ResponseEntity.status(HttpStatus.OK).body(byLocation);
	}

	@GetMapping("/posts/users/{userId}")
	public ResponseEntity<Object> findPostByUser(@PathVariable Long userId) {
		List<FindPostDto> byUser = postService.findByUser(userId);
		if (ObjectUtils.isEmpty(byUser)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(byUser);
		}
		return ResponseEntity.status(HttpStatus.OK).body(byUser);
	}

}
