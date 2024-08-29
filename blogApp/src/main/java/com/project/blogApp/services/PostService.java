package com.project.blogApp.services;



import com.project.blogApp.payload.PostDT;
import com.project.blogApp.payload.PostResponse;

public interface PostService{
	
	// create post
	
	PostDT createPost(PostDT postDT, int userId, int categoryId);
	
	// update post
	
	PostDT updatePost(PostDT postDT, int postId);
	
	// delete post
	
	void deletePost(int postId);
	
	// get all post
	
	PostResponse getAllPost(int pageNo, int pageSize , String sortBy , String sortDir);
	
	// get single
	
	PostDT getPostById(int postId);
	
	// get all post by category
	
	PostResponse getPostByCat(int categoryId, int pageNo , int pageSize, String sortBy , String sortDir);
	
	// get all post by user
	
	PostResponse getPostByUser(int userId , int pageNo , int pageSize, String sortBy , String sortDir);
	
	// search Post
	
	PostResponse searchPost(String keyWords ,int pageNo , int pageSize);

}
