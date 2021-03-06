package com.somaeja.post.service;

import com.somaeja.location.mapper.LocationMapper;
import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.dto.FindPostDto;
import com.somaeja.post.dto.ModifyPostDto;
import com.somaeja.post.entity.Post;
import com.somaeja.post.exception.ChangePostFailedException;
import com.somaeja.post.exception.NoSuchPostException;
import com.somaeja.post.exception.SavePostFailedException;
import com.somaeja.post.mapper.PostMapper;
import com.somaeja.post.exception.UserInfoNotMatchedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;
	private final LocationMapper locationMapper;

	public Post savePostInfo(CreatePostDto createDto, Long userId) {

		Long getLocationId = locationMapper.findLocationId(createDto.getLocation());
		// image 정보 조회, Image 테이블에 저장 -> ID 생성 -> ID 반환
		long imageId = 1;
		Post savePostInfo = createDto.toEntity(userId, getLocationId, imageId);

		int result = postMapper.save(savePostInfo);
		if (isNotReflected(result)) {
			log.info("post save Failed : user id = {} : The error may be caused by a internal server error ", userId);

			throw new SavePostFailedException("Save Failed : title = "
				+ savePostInfo.getTitle() + " user id =" + userId);
		}

		return savePostInfo;
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByAll() {
		return toDtoList(postMapper.findByAll());
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByTitle(String searchTitle) {
		return toDtoList(postMapper.findByTitle(searchTitle));
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByContent(String searchContent) {
		return toDtoList(postMapper.findByContent(searchContent));
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByLocation(Long locationId) {
		// location 정보 조회, ID 반환 location
		return toDtoList(postMapper.findByLocation(locationId));
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByUser(Long userId) {
		return toDtoList(postMapper.findByUser(userId));
	}


	public void deletePostInfo(Long postId, Long userId) {

		int result = postMapper.deletePost(postId, userId);
		if (isNotReflected(result)) {
			log.info("post delete failed : post id = {} : The error may have occurred because there is no post ID. ", postId);

			throw new NoSuchPostException(" post delete failed : post id = " + postId);
		}
	}

	public Post changePostInfo(Long postId, Long userId, ModifyPostDto changePostDto) {
		Long getLocationId = locationMapper.findLocationId(changePostDto.getLocation());

		String imageName = changePostDto.getImageName();

		Long imageId = 1L;

		Post targetPost = postMapper.findPostById(postId);
		if (ObjectUtils.isEmpty(targetPost)) {
			log.info("post find failed : post id = {} : The error may have occurred because there is no post ID. ", postId);

			throw new NoSuchPostException(" post find failed : post id = " + postId);
		}

		if (!targetPost.getUserId().equals(userId)) {
			log.info("user information match failed : not matched user id : id = {} but input id = {}",targetPost.getUserId(), userId);

			throw new UserInfoNotMatchedException(" user information match failed : not matched user id : id = "+targetPost.getUserId()+" but input id = " + userId);
		}


		Post changePostInfo = changePostDto.toEntity(targetPost.getId(), getLocationId, imageId);

		int result = postMapper.changePost(changePostInfo);
		if (isNotReflected(result)) {
			log.info("post changed failed : post id = {} : The error may be caused by a internal server error ", postId);

			throw new ChangePostFailedException(
				" post changed failed : post id = " + targetPost.getId() + " : user id = " + changePostDto.getUserId());
		}
		return changePostInfo;
	}


	private List<FindPostDto> toDtoList(List<Post> posts) {
		return posts.stream()
			.map(FindPostDto::of)
			.collect(Collectors.toList());
	}

	private boolean isNotReflected(int result) {
		return result < 1;
	}
}
