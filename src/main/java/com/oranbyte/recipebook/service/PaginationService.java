package com.oranbyte.recipebook.service;

import java.util.Map;

import org.springframework.data.domain.Page;

public interface PaginationService {

	<T> Map<String, Object> getPageMetadata(Page<T> page, int currentPageNo);
	
}
