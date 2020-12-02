package com.somaeja.post.service;

import com.somaeja.post.dto.CreatePostDto;
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
	public int savePost(CreatePostDto postDto){
		return postMapper.save(postDto);
	}

	// Post Find


	// Post Delete


	// Post Modify


}
