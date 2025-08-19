package com.oranbyte.recipebook.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.OrganizationSocialLinkDto;
import com.oranbyte.recipebook.repository.OrganizationSocialLinkRepository;
import com.oranbyte.recipebook.service.OrganizationSocialLinkService;

@Service
public class OrganizationSocialLinkServiceImpl implements OrganizationSocialLinkService{

	@Autowired
	private OrganizationSocialLinkRepository organizationSocialLinkRepository;
	
	@Override
	public List<OrganizationSocialLinkDto> getOrganizationSociaLinks() {
	    return organizationSocialLinkRepository.findAll().stream()
	        .map(link -> OrganizationSocialLinkDto.builder()
	            .platformName(link.getPlatform().getName())
	            .url(link.getUrl())
	            .build())
	        .toList();
	}


}
