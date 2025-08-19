package com.oranbyte.recipebook.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.recipebook.dto.UserDetailDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.entity.UserDetail;
import com.oranbyte.recipebook.exception.UserNotFoundException;

public interface UserDetailService {

	UserDetailDto getUserDetailDto(Long userId);
	
	UserDetail getUserDetailByUserId(Long userId);

	UserDetail getUserDetail(Long id);
	
	UserDetail saveUserDetail(UserDetail userDetail);
	
	UserDetailDto saveBannerImage(User user, MultipartFile image)  throws IOException, UserNotFoundException;
	
	UserDetail deleteBannerImage(UserDetail userDetail);
	
}
