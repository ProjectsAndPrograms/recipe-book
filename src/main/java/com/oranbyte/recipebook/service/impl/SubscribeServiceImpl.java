package com.oranbyte.recipebook.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.Subscribe;
import com.oranbyte.recipebook.repository.SubscribeRepository;
import com.oranbyte.recipebook.service.SubscribeService;

@Service
public class SubscribeServiceImpl implements SubscribeService{

	@Autowired
	private SubscribeRepository subscribeRepository;
	
	@Override
	public Subscribe save(Subscribe subscribe) {
		return subscribeRepository.save(subscribe);
	}

	@Override
	public boolean isEmailExists(String email) {
		return !Objects.isNull(subscribeRepository.findByEmail(email).orElse(null));
	}

}
