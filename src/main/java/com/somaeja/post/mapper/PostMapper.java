package com.somaeja.post.mapper;

import com.somaeja.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
	int save(Post post);

	Post findOne(Long postId);

	List<Post> findByAll();

	List<Post> findByTitle(String searchTitle);

	List<Post> findByContent(String searchContent);

	List<Post> findByLocation(Long locationId);

	List<Post> findByUser(Long userId);

	int deletePost(Long postId);

	int changePost(Post modifyPost);
}
