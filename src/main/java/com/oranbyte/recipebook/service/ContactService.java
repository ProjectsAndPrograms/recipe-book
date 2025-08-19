package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.dto.ContactDto;
import com.oranbyte.recipebook.entity.Contact;

public interface ContactService {

	Contact save(ContactDto contactDto);
	
}
