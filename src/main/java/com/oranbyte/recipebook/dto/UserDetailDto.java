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
public class UserDetailDto {

	private Long id;
	private String bio;
	private String websiteUrl;
	
	private String bannerImage;
	
}
