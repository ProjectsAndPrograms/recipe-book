package com.oranbyte.recipebook.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.recipebook.dto.UserDetailDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.entity.UserDetail;
import com.oranbyte.recipebook.exception.UserNotFoundException;
import com.oranbyte.recipebook.mapper.UserDetailMapper;
import com.oranbyte.recipebook.repository.UserDetailRepository;
import com.oranbyte.recipebook.service.FileService;
import com.oranbyte.recipebook.service.UserDetailService;

import jakarta.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private FileService fileService;

	@Override
	public UserDetailDto getUserDetailDto(Long userId) {
		return userDetailRepository.findByUserId(userId).map(UserDetailMapper::toDto).orElse(new UserDetailDto());

	}
	
	@Override
	public UserDetail getUserDetailByUserId(Long userId) {
		return userDetailRepository.findByUserId(userId).orElse(new UserDetail());
	}
	@Override
	public UserDetail getUserDetail(Long id) {
		return userDetailRepository.findById(id).orElse(new UserDetail());
	}


	@Transactional
	@Override
	public UserDetailDto saveBannerImage(User user, MultipartFile image) throws IOException, UserNotFoundException {
		if (image == null || image.isEmpty()) {
			throw new IllegalArgumentException("Uploaded banner image is empty or null");
		}


		UserDetail userDetail = userDetailRepository.findByUserId(user.getId())
				.orElse(new UserDetail());

		if(userDetail.getBannerImage() != null) {
			fileService.deleteIfExists(userDetail.getBannerImage());
		}
		
		String uploadFile = fileService.uploadFile(image, "banner");
		userDetail.setBannerImage(uploadFile);
		userDetailRepository.save(userDetail);

		return UserDetailMapper.toDto(userDetail);
	}

	@Override
	public UserDetail saveUserDetail(UserDetail userDetail) {
		return userDetailRepository.save(userDetail);
	}

	@Override
	public UserDetail deleteBannerImage(UserDetail userDetail) {
		
		if(userDetail.getBannerImage() != null) {
			fileService.deleteIfExists(userDetail.getBannerImage());
		}
		userDetail.setBannerImage(null);
		return userDetail;
	}

	
}
