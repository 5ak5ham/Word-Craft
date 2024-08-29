package com.project.blogApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogApp.entities.Category;

public interface CategoryRepositories extends JpaRepository<Category, Integer>{

}
