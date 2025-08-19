package com.oranbyte.recipebook.advice;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.oranbyte.recipebook.dto.OrganizationSocialLinkDto;
import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.mapper.UserMapper;
import com.oranbyte.recipebook.service.OrganizationSocialLinkService;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationSocialLinkService organizationSocialLinkService;

	@ModelAttribute
	public void addUserInfoToModel(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		boolean isAuthenticated = auth != null && auth.isAuthenticated()
				&& !"anonymousUser".equals(auth.getPrincipal());

		model.addAttribute("isAuthenticated", isAuthenticated);

		if (isAuthenticated) {
			Object principal = auth.getPrincipal();

			if (principal instanceof UserDetails userDetails) {
				model.addAttribute("currentUser", userDetails);
				model.addAttribute("username", userDetails.getUsername());
				model.addAttribute("roles", userDetails.getAuthorities());
			} else {
				model.addAttribute("username", principal.toString());
			}
		}
	}

	@ModelAttribute("authUser")
	public UserDto getLoggedInUser(Principal principal) {
		if (principal == null)
			return null;
		User user = userService.getUser(principal.getName());
		return UserMapper.toDto(user);
	}

	@ModelAttribute("organizationSocialLinks")
	public List<OrganizationSocialLinkDto> getOrganizationSocialLinks() {
		return organizationSocialLinkService.getOrganizationSociaLinks();
	}

	@ModelAttribute
	public void addUrlsToModel(HttpServletRequest request, Model model) {
		String baseUrl = request.getRequestURL().toString();
		String fullUrl = request.getQueryString() != null ? baseUrl + "?" + request.getQueryString() : baseUrl;

		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("fullUrl", fullUrl);
	}

	@ModelAttribute
	public void addPaginationUrlToModel(HttpServletRequest request, Model model) {
	    String baseUrl = request.getRequestURL().toString();
	    String queryString = request.getQueryString();
	    StringBuilder queryWithoutPage = new StringBuilder();

	    if (queryString != null) {
	        String[] params = queryString.split("&");
	        for (String param : params) {
	            if (!param.startsWith("page=")) {
	                if (queryWithoutPage.length() > 0) {
	                    queryWithoutPage.append("&");
	                }
	                queryWithoutPage.append(param);
	            }
	        }
	    }

	    String cleanedUrl = baseUrl;
	    if (queryWithoutPage.length() > 0) {
	        cleanedUrl += "?" + queryWithoutPage;
	    }

	    model.addAttribute("fullUrl", baseUrl + (queryString != null ? "?" + queryString : ""));
	    model.addAttribute("baseUrlWithoutPage", cleanedUrl);
	}

}
