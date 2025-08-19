package com.oranbyte.recipebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.SitePage;
import com.oranbyte.recipebook.repository.SitePageRepository;
import com.oranbyte.recipebook.service.SitePageService;

@Service
public class SitePageServiceImpl implements SitePageService{

	@Autowired
	private SitePageRepository sitePageRepository;
	
	@Override
	public SitePage saveSitePage(SitePage sitePage) {
		return sitePageRepository.save(sitePage);
	}

	@Override
	public SitePage getSitePage(String slug) {
		return sitePageRepository.findBySlug(slug);
	}

	@Override
	public SitePage getSitePage(Long id) {
		return sitePageRepository.findById(id).orElse(null);
	}

	
	
}
