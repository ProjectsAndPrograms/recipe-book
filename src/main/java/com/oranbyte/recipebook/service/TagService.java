package com.oranbyte.recipebook.service;

import java.util.List;
import java.util.Set;

import com.oranbyte.recipebook.dto.TagDto;
import com.oranbyte.recipebook.entity.Tag;

public interface TagService {

	Set<Tag> saveAll(String [] tagNames);
	
	TagDto getTag(Long id);
	
	List<TagDto> getTop20Tags();
}
