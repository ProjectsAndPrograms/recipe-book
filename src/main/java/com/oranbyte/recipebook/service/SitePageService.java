package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.entity.SitePage;

public interface SitePageService {
	
	SitePage saveSitePage(SitePage sitePage);
	
	SitePage getSitePage(String slug);
	
	SitePage getSitePage(Long id);

}
