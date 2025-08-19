package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.entity.Subscribe;

public interface SubscribeService {

	Subscribe save(Subscribe subscribe);
	
	boolean isEmailExists(String email);
	
}
