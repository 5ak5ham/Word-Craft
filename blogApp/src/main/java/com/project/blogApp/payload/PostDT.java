package com.project.blogApp.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDT {
	
	private int postId;
	
	@NotEmpty
	@Size(min = 4, message = "Title should contain atleast 4 or more characters")
	private String title;
	
	@NotEmpty
	@Size(min = 10, message = "Content should contain atleast 10 or more characters")
	private String content;
	
	private String imageName;
	
	private List<String> images;
	
	private Date date;
	
	
	private CategoryDT category;
	
	private UserDT user;
	
	private Set<CommentDT> comments = new HashSet<>();

}
