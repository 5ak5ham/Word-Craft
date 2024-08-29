package com.project.blogApp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.project.blogApp.payload.ApiResponse;
import com.project.blogApp.payload.UserDT;
import com.project.blogApp.services.FileService;
import com.project.blogApp.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	// CREATED USER
	
	@PostMapping("/")
	public ResponseEntity<UserDT> createUser(@Valid @RequestBody UserDT userDT){
		UserDT createUserDt = this.userService.createUser(userDT);
		return new ResponseEntity<>(createUserDt, HttpStatus.CREATED);
		
	}
	
	// UPDATED USER
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDT> updateUser(@Valid @RequestBody UserDT userDT, @PathVariable int userId){
		UserDT updatedUser = this.userService.updateUser(userDT, userId);
		return ResponseEntity.ok(updatedUser);
		
	}
	
	// DELETE USER
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId){
		
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully" , true), HttpStatus.OK);
	}
	
	// GET ALL USER
	
	@GetMapping("/")
	public ResponseEntity<List<UserDT>> allUsers(){
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	
	// GET SINGLE USER
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDT> getUser(@PathVariable int userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	
	
	// User IMAGE UPLOAD
	
	@PostMapping("/image/upload/{userId}")
	public ResponseEntity<UserDT> uploadUserImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable int userId) throws IOException{
		
		UserDT userDt = this.userService.getUserById(userId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		userDt.setImageName(fileName);
		UserDT updatedUser = this.userService.updateUser(userDt, userId);
		return new ResponseEntity<UserDT>(updatedUser , HttpStatus.OK);
	}
	
	//METHOD TO FETCH FILES
	
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadUserImage(
			@PathVariable String imageName,
			HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getImage(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource , response.getOutputStream());
		
	}
	
	
}
