package com.oranbyte.recipebook.mapper;

import com.oranbyte.recipebook.dto.UserDetailDto;
import com.oranbyte.recipebook.entity.UserDetail;

public class UserDetailMapper {
	public static UserDetailDto toDto(UserDetail userDetail) {
		return UserDetailDto.builder()
				.id(userDetail.getId())
				.bio(userDetail.getBio())
				.websiteUrl(userDetail.getWebsiteUrl())
				.bannerImage(userDetail.getBannerImageUrl())
				.build();
	}
}
