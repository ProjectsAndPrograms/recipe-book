package com.oranbyte.recipebook.service;

import java.util.List;

import com.oranbyte.recipebook.dto.UserSocialLinkDto;

public interface UserSocialLinksService {
	
	List<UserSocialLinkDto> getUserSocialLinks(Long userId); 

}
