package com.oranbyte.recipebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationSocialLinkDto {

	private Long id;
	
	private String url;
	
	private String platformName;
	
}
