package com.oranbyte.recipebook.dto;

import java.util.Map;

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
public class EmailRequest {
	
	private String receiver;
	private String subject;
	private String templateName;
	Map<String, Object> model;

}
