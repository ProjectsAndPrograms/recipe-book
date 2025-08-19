package com.oranbyte.recipebook.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UrlHelper {

	public String recipeUrl(HttpServletRequest request, Long recipeId) {
		if (request == null) {
			return "/recipes/" + recipeId;
		}
		return request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() != 80 && request.getServerPort() != 443 ? ":" + request.getServerPort() : "")
				+ "/recipes/" + recipeId;
	}
}
