package com.somaeja.post.mapper;

import com.somaeja.post.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper {
	int save(Post post);

	Post findByTitle(String searchTitle);

	Post findByLocation(Long locationId);

	Post findByUser(Long userId);
}
