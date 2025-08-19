package com.oranbyte.recipebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tags")
public class TagController {

	
	@GetMapping
	public String index() {
		return "tags";
	}
	
}
