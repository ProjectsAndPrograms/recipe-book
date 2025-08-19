package com.oranbyte.recipebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSocialLinkDto {
	
	private Long id;
	private String platformName;
	private String url;
	private String baseUrl;
	private Long user_id;
	
}
