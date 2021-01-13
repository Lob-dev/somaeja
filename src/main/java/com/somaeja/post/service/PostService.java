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
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;
	private final LocationMapper locationMapper;

	// Post Create
	public Post savePostInfo(CreatePostDto createDto) {
		// user 아이디 조회, User Id는 Login 정보로 반환...? 인터셉터(인증)?
		long userId = 1;

		Long getLocationId = locationMapper.findLocationId(createDto.getLocation());
		// image 정보 조회, Image 테이블에 저장 -> ID 생성 -> ID 반환
		long imageId = 1;
		Post savePostInfo = createDto.toEntity(userId, getLocationId, imageId);

		int result = postMapper.save(savePostInfo);
		if (wasReflected(result)) {
			log.info("post save Failed : user id = {} : The error may be caused by a internal server error ", userId);

			throw new SavePostFailedException("Save Failed : title = "
				+ savePostInfo.getTitle() + " user id =" + userId);
		}

		return savePostInfo;
	}

	// Post Find
	@Transactional(readOnly = true)
	public List<FindPostDto> findByAll() {
		List<Post> posts = postMapper.findByAll();
		return toDtoList(posts);
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByTitle(String searchTitle) {
		List<Post> posts = postMapper.findByTitle(searchTitle);
		return toDtoList(posts);
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByContent(String searchContent) {
		List<Post> posts = postMapper.findByContent(searchContent);
		return toDtoList(posts);
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByLocation(Long locationId) {
		// location 정보 조회, ID 반환 location
		List<Post> posts = postMapper.findByLocation(locationId);
		return toDtoList(posts);
	}

	@Transactional(readOnly = true)
	public List<FindPostDto> findByUser(Long userId) {
		List<Post> posts = postMapper.findByUser(userId);
		return toDtoList(posts);
	}


	// Post Delete
	public void deletePostInfo(Long postId) {
		int result = postMapper.deletePost(postId);
		if (wasReflected(result)) {
			log.info("post delete failed : post id = {} : The error may have occurred because there is no post ID. ", postId);

			throw new NoSuchPostException(" post delete failed : post id = " + postId);
		}
	}

	// Post Modify
	@Transactional
	public Post changePostInfo(Long postId, ModifyPostDto changePostDto) {
		Long getLocationId = locationMapper.findLocationId(changePostDto.getLocation());

		String imageName = changePostDto.getImageName();
		// Long imageId = imageMapper.findImage(imageName);
		Long imageId = 1L;

		Long hasFind = postMapper.findPostById(postId);
		if (ObjectUtils.isEmpty(hasFind)) {
			log.info("post find failed : post id = {} : The error may have occurred because there is no post ID. ", postId);

			throw new NoSuchPostException(" post find failed : post id = " + postId);
		}

		Post changePostInfo = changePostDto.toEntity(hasFind, getLocationId, imageId);

		int result = postMapper.changePost(changePostInfo);
		if (wasReflected(result)) {
			log.info("post changed failed : post id = {} : The error may be caused by a internal server error ", postId);

			throw new ChangePostFailedException(
				" post changed failed : post id = " + hasFind +" : user id = " + changePostDto.getUserId());
		}
		return changePostInfo;
	}


	private List<FindPostDto> toDtoList(List<Post> posts) {
		List<FindPostDto> postDtoList = new ArrayList<>();
		for (Post post : posts) {
			FindPostDto postDto = FindPostDto.of(post);
			postDtoList.add(postDto);
		}
		return postDtoList;
	}

	private boolean wasReflected(int hasDeleted) {
		return hasDeleted < 1;
	}
}
