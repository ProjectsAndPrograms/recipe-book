package com.oranbyte.recipebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.ContactDto;
import com.oranbyte.recipebook.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("contactDto", new ContactDto());
		return "contact/contact";
	}
	
	@PostMapping
	public String store(@ModelAttribute ContactDto contactDto, RedirectAttributes redirectAttr, HttpServletRequest request) {
		try {
            contactService.save(contactDto);
            redirectAttr.addFlashAttribute("success", "Your recipe created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttr.addFlashAttribute("error", e.getMessage());
        }
		 return "redirect:" + request.getHeader("Referer");
	}
	
}
