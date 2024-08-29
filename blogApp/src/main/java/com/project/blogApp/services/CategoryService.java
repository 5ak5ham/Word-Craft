package com.project.blogApp.services;

import java.util.List;

import com.project.blogApp.payload.CategoryDT;

public interface CategoryService {

	
	CategoryDT createCategory(CategoryDT categoryDT);
	
	CategoryDT updateCategory(CategoryDT categoryDT , int categoryId);
	
	CategoryDT getCategoryById(int categoryId);
	
	List<CategoryDT> getAllCategory();
	
	void deleteCategory(int categoryId);
	
	
}
