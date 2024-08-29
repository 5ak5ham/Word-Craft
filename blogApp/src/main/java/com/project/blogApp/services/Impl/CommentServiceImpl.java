package com.project.blogApp.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogApp.entities.Comment;
import com.project.blogApp.entities.Post;
import com.project.blogApp.entities.User;
import com.project.blogApp.exceptions.ResourceNotFoundException;
import com.project.blogApp.payload.CommentDT;
import com.project.blogApp.repositories.CommentRepository;
import com.project.blogApp.repositories.PostRepositories;
import com.project.blogApp.repositories.UserRepositories;
import com.project.blogApp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private UserRepositories userRepo;
	
	@Autowired
	private PostRepositories postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDT createComment(CommentDT commentDT, int postId , int userId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", " postId " , postId ));
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", " id " , userId ));
		
		
		Comment comment = this.modelMapper.map(commentDT, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDT.class);
	}

	@Override
	public void deleteComment(int commentId) {
		
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", " commentId " , commentId ));;
		
		this.commentRepo.delete(comment);
	}
	
	

}
