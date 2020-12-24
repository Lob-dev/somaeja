package com.somaeja.post.service;

import com.somaeja.location.mapper.LocationMapper;
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
	private final LocationMapper locationMapper;

	// Post Create
	@Transactional
	public Post savePostInfo(CreatePostDto createDto) {
		// user 아이디 조회, User Id는 Login 정보로 반환...? 인터셉터(인증)?
		long userId = 1;

		Long locationId = locationMapper.findLocationId(createDto.getLocation());
		// image 정보 조회, Image 테이블에 저장 -> ID 생성 -> ID 반환
		long imageId = 1;
		Post savePostInfo = createDto.toEntity(userId, locationId, imageId);

		int hasSaved = postMapper.save(savePostInfo);
		if (hasSaved < 1) {
			// controller advice 에서 catch..? -> internal Server Error
			throw new SavePostFailedException("Save Failed :: TITLE ="
				+ savePostInfo.getTitle() + " USER ID =" + userId);
		}

		return savePostInfo;
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

	public List<FindPostDto> findByContent(String searchContent) {
		List<Post> postsByContent = postMapper.findByContent(searchContent);
		return toDtoList(postsByContent);
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

	public void deletePostInfo(Long postId) {
		int hasDeleted = postMapper.deletePost(postId);
		if (hasDeleted < 1) {
			throw new NoSuchPostException("Delete Post Fail :: ID = " + postId);
		}
	}

	// Post Modify

	public Post changePostInfo(Long postId, ModifyPostDto changePostDto) {
		Long locationId = locationMapper.findLocationId(changePostDto.getLocation());

		String imageName = changePostDto.getImageName();
		// Long imageId = imageMapper.findImage(imageName);
		Long imageId = 1L;

		Long hasFind = postMapper.findPostById(postId);
		if (ObjectUtils.isEmpty(hasFind)){
			throw new NoSuchPostException("Post Find Failed :: ID = " + postId);
		}

		Post changePostInfo = changePostDto.toEntity(hasFind, locationId, imageId);

		int hasChanged = postMapper.changePost(changePostInfo);
		if (hasChanged < 1) {
			throw new ModifyPostFailedException(
				"Change Post Fail :: ID = " + hasFind + " USER ID =" + changePostDto.getUserId());
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
}
