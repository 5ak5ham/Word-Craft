package com.project.blogApp.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDT {
	
	
	private int categoryId;
	
	@NotEmpty
	@Size(min = 4, message = "Category Type should contain atleast 4 or more characters")
	private String categoryType;
	
	@NotEmpty
	@Size(min = 10 , message = "Please describe about the title of categories title")
	private String categoryDescription;

}
