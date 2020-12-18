package com.somaeja.post.service;

import com.somaeja.post.dto.CreatePostDto;
import com.somaeja.post.dto.FindPostDto;
import com.somaeja.post.dto.ModifyPostDto;
import com.somaeja.post.entity.Post;
import com.somaeja.post.exception.ModifyPostFailedException;
import com.somaeja.post.exception.NoSuchPostException;
import com.somaeja.post.exception.SavePostFailedException;
import com.somaeja.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostMapper postMapper;

	// Post Create
	@Transactional
	public Post savePostInfo(CreatePostDto createDto) {
		// user 아이디 조회, User Id는 Login 정보로 반환...? 인터셉터(인증)?
		long userId = 1;
		// location 정보 조회, Location 탐색 = ID 확인 -> ID 반환
		// locationId = LocationService.findLocation(createDto.getLocation());
		long locationId = 1;
		// image 정보 조회, Image 테이블에 저장 -> ID 생성 -> ID 반환
		long imageId = 1;
		Post savePost = createDto.toEntity(userId, locationId, imageId);

		int hasSave = postMapper.save(savePost);
		if (hasSave < 1) {
			// controller advice 에서 catch..? -> internal Server Error
			throw new SavePostFailedException("Save Failed :: TITLE ="
				+ savePost.getTitle() + " USER ID =" + userId);
		}

		return savePost;
	}

	// Post Find
	public List<FindPostDto> findByAll() {
		List<Post> postsByAll = postMapper.findByAll();
		return toDtoList(postsByAll);
	}

	public List<FindPostDto> findByTitle(String searchTitle) {
		List<Post> postsByTitle = postMapper.findByTitle(searchTitle);
		return toDtoList(postsByTitle);
	}

	public List<FindPostDto> findByLocation(Long locationId) {
		// location 정보 조회, ID 반환 location
		List<Post> postsByLocation = postMapper.findByLocation(locationId);
		return toDtoList(postsByLocation);
	}

	public List<FindPostDto> findByUser(Long userId) {
		List<Post> postsByUser = postMapper.findByUser(userId);
		return toDtoList(postsByUser);
	}


	// Post Delete

	public int deletePostInfo(Long postId) {
		int hasDelete = postMapper.deletePost(postId);
		if (hasDelete < 1) {
			throw new NoSuchPostException("Delete Post Fail :: ID = " + postId);
		}
		return hasDelete;
	}

	// Post Modify

	public Post changePostInfo(Long postId, ModifyPostDto modifyPostDto) {
		String location = modifyPostDto.getLocation();
		// Long locationId = locationMapper.findLocation(loacation);
		Long locationId = 1L;

		String imageName = modifyPostDto.getImageName();
		// Long imageId = imageMapper.findImage(imageName);
		Long imageId = 1L;
		Post savedPost = postMapper.findOne(postId);
		if (ObjectUtils.isEmpty(savedPost)){
			throw new NoSuchPostException("Post Find Failed :: ID = " + postId);
		}

		Post updateEntity = modifyPostDto.toEntity(savedPost.getId(), locationId, imageId);
		int hasModify = postMapper.changePost(updateEntity);
		if (hasModify < 1) {
			throw new ModifyPostFailedException(
				"Modify Post Fail :: ID = " + postId + " USER ID =" + savedPost.getUserId());
		}
		return updateEntity;
	}

	private List<FindPostDto> toDtoList(List<Post> posts) {
		List<FindPostDto> postDtoList = new ArrayList<>();
		for (Post post : posts) {
			FindPostDto postDto = FindPostDto.of(post);
			postDtoList.add(postDto);
		}
		return postDtoList;
	}
}
