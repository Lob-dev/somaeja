package com.somaeja.post.mapper;

import com.somaeja.post.dto.CreatePostDto;
import org.springframework.stereotype.Repository;


@Repository
public interface PostMapper {

	int save(CreatePostDto postDto);

}
