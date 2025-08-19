package com.oranbyte.recipebook.mapper;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.entity.UserDetail;
import com.oranbyte.recipebook.util.DateUtil;

public class UserMapper {

	public static UserDto toDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.username(user.getUsername())
				.email(user.getEmail())
				.role(user.getRole())
				.image(user.getImageUrl())
				.bio(Optional.ofNullable(user.getUserDetail())
			             .map(UserDetail::getBio)
			             .orElse(null)
			    )
				.url(Optional.ofNullable(user.getUserDetail())
			             .map(UserDetail::getWebsiteUrl)
			             .orElse(null))
				.socialLinks(
					    Optional.ofNullable(user.getSocialLinks())
					        .orElse(Collections.emptyList())
					        .stream()
					        .map(UserSocialLinkMapper::toMap)
					        .flatMap(map -> map.entrySet().stream())
					        .collect(Collectors.toMap(
					            Map.Entry::getKey,
					            Map.Entry::getValue,
					            (existing, replacement) -> replacement
					        ))
					)
				.followers(
						user.getFollowers().size()
						)
				.following(user.getFollowing().size())
				.createdAt(DateUtil.dateTimeFormat(user.getCreatedAt()))
				.deletedAt(DateUtil.dateTimeFormat(user.getDeletedAt()))
				.build();
	}
	
}
