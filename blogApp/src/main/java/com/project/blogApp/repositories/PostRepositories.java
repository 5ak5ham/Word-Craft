package com.project.blogApp.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogApp.entities.Category;
import com.project.blogApp.entities.Post;
import com.project.blogApp.entities.User;

public interface PostRepositories extends JpaRepository<Post, Integer>{
	
	Page<Post> findByUser(User user , Pageable pages);
	
	Page<Post> findByCategory(Category category , Pageable pages);
	
	Page<Post> findByTitleContaining(String title , Pageable pages); 

}
