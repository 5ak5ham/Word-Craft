package com.project.blogApp.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogApp.entities.Category;
import com.project.blogApp.exceptions.ResourceNotFoundException;
import com.project.blogApp.payload.CategoryDT;
import com.project.blogApp.repositories.CategoryRepositories;
import com.project.blogApp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepositories categoryRepositories;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDT createCategory(CategoryDT categoryDT) {
		
		Category category = this.modelMapper.map(categoryDT, Category.class);
		
		Category addedCategory = this.categoryRepositories.save(category);
		
		return this.modelMapper.map(addedCategory, CategoryDT.class);
	}

	@Override
	public CategoryDT updateCategory(CategoryDT categoryDT, int categoryId) {
		
		Category category = this.categoryRepositories.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", " categoryId " , categoryId ));
		
		category.setCategoryDescription(categoryDT.getCategoryDescription());
		category.setCategoryType(categoryDT.getCategoryType());
		
		Category updatedCategory = this.categoryRepositories.save(category);
		
		return this.modelMapper.map(updatedCategory, CategoryDT.class);
	}

	@Override
	public CategoryDT getCategoryById(int categoryId) {
		
		Category category = this.categoryRepositories.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", " categoryId " , categoryId ));
		
		return this.modelMapper.map(category, CategoryDT.class);
	}

	@Override
	public List<CategoryDT> getAllCategory() {
		
		List<Category> categories = this.categoryRepositories.findAll();
		
		List<CategoryDT> categoryDTs = categories.stream().map((category) -> 
		this.modelMapper.map(category, CategoryDT.class)).collect(Collectors.toList());
		
		return categoryDTs;
	}

	@Override
	public void deleteCategory(int categoryId) {
		
		Category category = this.categoryRepositories.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", " categoryId " , categoryId ));
		
		this.categoryRepositories.delete(category);
		
	}

}
