package com.oranbyte.recipebook.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
public class UserDto {

	private Long id;
	private String name;
	private String username;
	private String email;
	private String role;
	private String image;
	private String bio;
	private String url;
	private Map<String, String> socialLinks; 
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
	
	private int recipeCount;
	private long followers;
	private long following;
	private long likeCount;
	private long dislikeCount;
	
	private String createdAt;
	private String deletedAt;
}
