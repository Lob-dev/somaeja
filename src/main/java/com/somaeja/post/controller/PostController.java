package com.somaeja.post.controller;

import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.entity.OfferStatus;
import com.somaeja.post.entity.TradeStatus;
import com.somaeja.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/create/post")
public class PostController {

	private final PostService postService;
	private final PostValidator postValidator;

	@PostMapping
	public ResponseEntity<String> createPost(@RequestBody CreatePostDto postDto, Errors errors){

		postValidator.validate(postDto,errors);
		if (errors.hasErrors()){
			String errorsBody = errors.getAllErrors().toString();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsBody);
		}

		// Mocking Data
		postDto.setOfferStatus(OfferStatus.YES);
		postDto.setTradeStatus(TradeStatus.OFFLINE);

		// Image files -> Stream -> resources / static / (여기에 저장)
		// Image 테이블에 저장 -> ID 생성 -> ID 반환 -> postDto.setImageId(imageId);
		// Location 문자열 -> Location 탐색 = ID 확인 -> ID 반환 -> postDto.setLocation(locationId);
		// User Id는 Login 정보로 반환...?

		postDto.setCreatedDate(LocalDateTime.now());
		postDto.setModifyDate(LocalDateTime.now());

		int isSave = postService.savePost(postDto);

		if (isSave <= 0){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("false");
		}
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

}
