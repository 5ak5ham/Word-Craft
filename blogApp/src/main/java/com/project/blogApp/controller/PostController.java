package com.project.blogApp.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blogApp.config.AppConstants;
import com.project.blogApp.payload.ApiResponse;
import com.project.blogApp.payload.PostDT;
import com.project.blogApp.payload.PostResponse;
import com.project.blogApp.services.FileService;
import com.project.blogApp.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDT> createPost(@Valid @RequestBody PostDT postDT,
			@PathVariable int userId,
			@PathVariable int categoryId){
		
		PostDT createdPost = this.postService.createPost(postDT, userId, categoryId);
		return new ResponseEntity<PostDT>(createdPost, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@RequestParam(value = "pageNo", defaultValue = AppConstants.Page_No , required = false) int pageNo,
	@RequestParam(value = "pageSize", defaultValue = AppConstants.Page_Size , required = false) int pageSize ,
	@RequestParam(value = "sortBy", defaultValue = AppConstants.Sort_By , required = false) String sortBy ,
	@RequestParam(value = "sortDir", defaultValue = AppConstants.Sort_Dir , required = false) String sortDir,
	@PathVariable int userId){
		
		PostResponse postsDts = this.postService.getPostByUser(userId, pageNo , pageSize , sortBy , sortDir);
		
		return new ResponseEntity<PostResponse>(postsDts, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@RequestParam(value = "pageNo", defaultValue = AppConstants.Page_No , required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.Page_Size, required = false) int pageSize ,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.Sort_By , required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.Sort_Dir , required = false) String sortDir
			,@PathVariable int categoryId){
		
		PostResponse postsDts = this.postService.getPostByCat(categoryId,pageNo, pageSize , sortBy , sortDir);
		
		return new ResponseEntity<PostResponse>(postsDts, HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> allPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.Page_No , required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.Page_Size , required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.Sort_By , required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.Sort_Dir , required = false) String sortDir){
		
		return ResponseEntity.ok(this.postService.getAllPost(pageNo , pageSize ,sortBy , sortDir));
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDT> postById(@PathVariable int postId) {
		
		return ResponseEntity.ok(this.postService.getPostById(postId));
	}
	
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId){
		
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully" , true), HttpStatus.OK);
		
	}
	
	@PutMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDT> updatePost(@Valid @RequestBody PostDT postDT , @PathVariable int postId){
		
		PostDT post = this.postService.updatePost(postDT, postId);
		
		return new ResponseEntity<PostDT>(post, HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<PostResponse> searchPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.Page_No , required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.Page_Size , required = false) int pageSize,
			@PathVariable String keyword)
	{
		return ResponseEntity.ok(this.postService.searchPost(keyword, pageNo , pageSize));
	}
	
	// POST IMAGE UPLOAD
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDT> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable int postId) throws IOException{
		
		PostDT postDt = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		postDt.setImageName(fileName);
		PostDT updatedPost = this.postService.updatePost(postDt, postId);
		return new ResponseEntity<PostDT>(updatedPost , HttpStatus.OK);
	}
	
	//METHOD TO FETCH FILES
	
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getImage(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource , response.getOutputStream());
		
	}
	
	
	@PostMapping("/posts/images/upload/{postId}")
	public ResponseEntity<PostDT> uploadMultipeFiles(
			@RequestParam("images") MultipartFile[] images,
			@PathVariable int postId
			) throws Exception{
		
		PostDT postDt = this.postService.getPostById(postId);
			
		List<String> files = this.fileService.uploadImages(path, images);
		
		postDt.setImages(files);
		
		PostDT updatedPost = this.postService.updatePost(postDt, postId);
		
		return new ResponseEntity<PostDT>(updatedPost , HttpStatus.OK);
		
		
	}
	
	
	
	
	
	
	
	
	
}
