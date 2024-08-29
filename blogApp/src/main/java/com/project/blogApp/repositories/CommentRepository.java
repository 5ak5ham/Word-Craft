package com.project.blogApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogApp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
