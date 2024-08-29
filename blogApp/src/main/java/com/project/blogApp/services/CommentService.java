package com.project.blogApp.services;

import com.project.blogApp.payload.CommentDT;

public interface CommentService {

	
	CommentDT createComment(CommentDT commentDT, int postId , int userId);
	
	void deleteComment (int commentId);
}
