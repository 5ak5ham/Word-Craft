package com.project.blogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.blogApp.payload.*;
import com.project.blogApp.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("users/{userId}/posts/{postId}/comments")
	public ResponseEntity<CommentDT> createComment(@Valid @RequestBody CommentDT commentDt ,
			@PathVariable int postId , @PathVariable int userId){
		
		
		CommentDT createdComment = this.commentService.createComment(commentDt, postId, userId);
		
		return new ResponseEntity<CommentDT>(createdComment , HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId){
		
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true) , HttpStatus.OK);
	}

}
