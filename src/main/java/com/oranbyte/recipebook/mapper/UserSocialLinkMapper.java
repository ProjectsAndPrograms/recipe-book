package com.oranbyte.recipebook.mapper;

import java.util.Map;

import com.oranbyte.recipebook.dto.UserSocialLinkDto;
import com.oranbyte.recipebook.entity.SocialMediaPlatform;
import com.oranbyte.recipebook.entity.UserSocialLink;

public class UserSocialLinkMapper {

	public static UserSocialLinkDto toDto(UserSocialLink userSocialLink) {
		SocialMediaPlatform platform = userSocialLink.getPlatform();
		return UserSocialLinkDto.builder().id(userSocialLink.getId()).platformName(platform.getName())
				.url(userSocialLink.getUrl()).baseUrl(platform.getBaseUrl()).build();
	}
	
	
	public static Map<String, String> toMap(UserSocialLink userSocialLink) {
		SocialMediaPlatform platform = userSocialLink.getPlatform();
		return Map.of(platform.getName(), userSocialLink.getUrl());
	}

}
