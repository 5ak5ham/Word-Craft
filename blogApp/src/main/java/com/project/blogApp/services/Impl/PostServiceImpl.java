package com.project.blogApp.services.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.blogApp.entities.Category;
import com.project.blogApp.entities.Post;
import com.project.blogApp.entities.User;
import com.project.blogApp.exceptions.ResourceNotFoundException;
import com.project.blogApp.payload.PostDT;
import com.project.blogApp.payload.PostResponse;
import com.project.blogApp.repositories.CategoryRepositories;
import com.project.blogApp.repositories.PostRepositories;
import com.project.blogApp.repositories.UserRepositories;
import com.project.blogApp.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepositories postRepo;
	
	@Autowired	
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepositories catRepo;
	
	@Autowired
	private UserRepositories userRepo;
	
	
	// CREATE POST

	@Override
	public PostDT createPost(PostDT postDt, int userId, int categoryId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", " id " , userId ));
		Category category = this.catRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", " categoryId " , categoryId ));
		
		
		Post post = this.modelMapper.map(postDt, Post.class);
		post.setCategory(category);
		post.setDate(new Date());
		post.setUser(user);
		post.setImageName("default.png");
		post.setImages(new ArrayList<>());
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDT.class);		
		
	}
	
	// UPDATE POSTS

	@Override
	public PostDT updatePost(PostDT postDT, int postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", " postId " , postId ));
		
		post.setTitle(postDT.getTitle());
		post.setContent(postDT.getContent());
		post.setImageName(postDT.getImageName());
		post.setImages(postDT.getImages());
		
		
		this.postRepo.save(post);
		return this.modelMapper.map(post, PostDT.class);
	}

	
	// DELETE POSTS
	
	@Override
	public void deletePost(int postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", " postId " , postId ));
		
		this.postRepo.delete(post);
		
	}
	
	// GET ALL POSTS

	@Override
	public PostResponse getAllPost(int pageNo, int pageSize , String sortBy , String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pages = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pagePosts = this.postRepo.findAll(pages);
		List<Post> posts =  pagePosts.getContent();
		
		
		List<PostDT> postDTs = posts.stream().map((post) -> this.modelMapper.map(post,PostDT.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDTs);
		postResponse.setPageNo(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
		
	}

	
	// GET POSTS BY POST ID 
	
	@Override
	public PostDT getPostById(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", " postId " , postId ));
		
		return this.modelMapper.map(post, PostDT.class);
	}
	
	// GET ALL POST OF A CERTAIN CATEGORY

	@Override
	public PostResponse getPostByCat(int categoryId , int pageNo , int pageSize , String sortBy , String sortDir) {
		
		Category category = this.catRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", " categoryId " , categoryId ));
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pages = PageRequest.of(pageNo, pageSize ,sort);
		Page<Post> pagePosts = this.postRepo.findByCategory(category , pages);
		List<Post> postsList =  pagePosts.getContent();
		
		List<PostDT> postDts =  postsList.stream().map(post -> this.modelMapper.map(post, PostDT.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDts);
		postResponse.setPageNo(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
		
	}
	
	// GET ALL POSTS FROM A CERTAIN USER

	@Override
	public PostResponse getPostByUser(int userId, int pageNo , int pageSize , String sortBy , String sortDir) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", " id " , userId ));
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pages = PageRequest.of(pageNo, pageSize ,sort);
		Page<Post> pagePosts = this.postRepo.findByUser(user , pages);
		List<Post> postsList =  pagePosts.getContent();
		
		List<PostDT> postDts =  postsList.stream().map(post -> this.modelMapper.map(post, PostDT.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDts);
		postResponse.setPageNo(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
		
	}

	@Override
	public PostResponse searchPost(String keyWords , int pageNo, int pageSize) {
		
		Pageable pages = PageRequest.of(pageNo, pageSize);
		
		Page<Post> pagePosts = this.postRepo.findByTitleContaining(keyWords , pages);
		List<Post> posts = pagePosts.getContent();
		
		List<PostDT> postDTs = posts.stream().map(post -> this.modelMapper.map(post, PostDT.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDTs);
		postResponse.setPageNo(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		return postResponse;
	}

}
