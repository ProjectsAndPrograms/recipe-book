package com.oranbyte.recipebook.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.service.PaginationService;

@Service
public class PaginationServiceImpl implements PaginationService {

	@Override
	public <T> Map<String, Object> getPageMetadata(Page<T> page, int currentPageNo) {
	    Map<String, Object> map = new HashMap<>();

	    int totalPages = page.getTotalPages();
	    int maxVisiblePages = 5;
	    int half = maxVisiblePages / 2;

	    int startPage = Math.max(0, currentPageNo - half);
	    int endPage = Math.min(totalPages - 1, startPage + maxVisiblePages - 1);

	    if (endPage - startPage < maxVisiblePages - 1) {
	        startPage = Math.max(0, endPage - maxVisiblePages + 1);
	    }
	    
	    int size = page.getSize();
	    map.put("currentPage", currentPageNo + 1);
	    map.put("totalPages", totalPages);
	    map.put("startPage", startPage + 1);
	    map.put("endPage", endPage + 1);
	    map.put("firstItemIndex", currentPageNo * size - size);

	    return map;
	}


}
