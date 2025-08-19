package com.oranbyte.recipebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.ContactDto;
import com.oranbyte.recipebook.entity.Contact;
import com.oranbyte.recipebook.repository.ContactRepository;
import com.oranbyte.recipebook.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public Contact save(ContactDto dto) {
		return contactRepository.save(Contact.builder().name(dto.getName()).email(dto.getEmail())
				.reason(dto.getReason()).description(dto.getDescription()).build());

	}

}
