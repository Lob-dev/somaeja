package com.somaeja.post.mapper;

import com.somaeja.post.entity.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
	int save(Post post);

	Post findPostById(Long postId);

	List<Post> findByAll();

	List<Post> findByTitle(String searchTitle);

	List<Post> findByContent(String searchContent);

	List<Post> findByLocation(Long locationId);

	List<Post> findByUser(Long userId);

	int deletePost(@Param("postId") Long postId, @Param("userId") Long userId);

	int changePost(Post modifyPost);
}
