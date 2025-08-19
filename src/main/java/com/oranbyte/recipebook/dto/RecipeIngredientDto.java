package com.oranbyte.recipebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecipeIngredientDto {

	private Long id;
	private String name;
	private String quantity;
	private String unit;
	private String note;
	
	private Long unitId;
	
	
}
