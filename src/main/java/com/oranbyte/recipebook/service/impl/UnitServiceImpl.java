package com.oranbyte.recipebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.Unit;
import com.oranbyte.recipebook.repository.UnitRepository;
import com.oranbyte.recipebook.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService{

	@Autowired
	private UnitRepository unitRepository;
	
	@Override
	public List<Unit> getAll() {
		return unitRepository.findAll();
	}

}
