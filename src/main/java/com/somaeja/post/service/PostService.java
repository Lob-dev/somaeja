package com.somaeja.post.service;

import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.entity.Post;
import com.somaeja.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostMapper postMapper;

	// Post Create
	@Transactional
	public int savePost(CreatePostDto createDto) {
		// user 아이디 조회, User Id는 Login 정보로 반환...? 인터셉터(인증)?
		long userId = 1;
		// location 정보 조회, Location 탐색 = ID 확인 -> ID 반환
		// locationId = LocationService.findLocation(createDto.getLocation());
		long locationId = 1;
		// image 정보 조회, Image 테이블에 저장 -> ID 생성 -> ID 반환
		long imageId = 1;

		Post post = createDto.toEntity(userId, locationId, imageId);
		return postMapper.save(post);
	}


	// Post Find


	// Post Delete


	// Post Modify


}
