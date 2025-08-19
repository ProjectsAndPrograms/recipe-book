package com.oranbyte.recipebook.controller.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.oranbyte.recipebook.entity.SitePage;
import com.oranbyte.recipebook.service.SitePageService;

@Controller
@RequestMapping("/privacy-policy")
public class PrivacyPolicyController {

	@Autowired
	private SitePageService sitePageService;

	@GetMapping
	public String index(Model model) {

		SitePage sitePage = sitePageService.getSitePage("privacy-policy");

		if (sitePage == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
		}

		model.addAttribute("sitePage", sitePage);

		return "site-page/site-page";
	}

}
