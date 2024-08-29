package com.project.blogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogApp.payload.ApiResponse;
import com.project.blogApp.payload.CategoryDT;
import com.project.blogApp.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDT> createCategory(@Valid @RequestBody CategoryDT categoryDT){
		
		CategoryDT createCategoryDT = this.categoryService.createCategory(categoryDT);
		
		return new ResponseEntity<>(createCategoryDT , HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDT> updateCategory(@Valid @RequestBody CategoryDT categoryDT, @PathVariable int categoryId){
		
		CategoryDT category = this.categoryService.updateCategory(categoryDT, categoryId);
		
		return ResponseEntity.ok(category);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId){
		
		
		this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully" , true), HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDT> getCategory(@PathVariable int categoryId){
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDT>> getAllCategories(){
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}
	
	

}

























