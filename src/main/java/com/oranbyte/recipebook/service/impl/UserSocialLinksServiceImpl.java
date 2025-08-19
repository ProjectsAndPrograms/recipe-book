package com.oranbyte.recipebook.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.UserSocialLinkDto;
import com.oranbyte.recipebook.mapper.UserSocialLinkMapper;
import com.oranbyte.recipebook.repository.UserSocialLinkRepository;
import com.oranbyte.recipebook.service.UserSocialLinksService;

@Service
public class UserSocialLinksServiceImpl implements UserSocialLinksService {

	@Autowired
	private UserSocialLinkRepository userSocialLinkRepository;

	@Override
	public List<UserSocialLinkDto> getUserSocialLinks(Long userId) {

		return userSocialLinkRepository.findByUserId(userId).stream().filter(Optional::isPresent).map(Optional::get)
				.map(UserSocialLinkMapper::toDto).toList();
	}

}
