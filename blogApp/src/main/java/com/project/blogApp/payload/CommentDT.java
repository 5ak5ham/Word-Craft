package com.project.blogApp.payload;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentDT {
	
	private int commentId;
	
	@NotEmpty
	private String content;
	
}
