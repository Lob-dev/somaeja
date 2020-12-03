package com.somaeja.post.mapper;

import com.somaeja.post.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper {
	int save(Post post);

}
