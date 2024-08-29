package com.project.blogApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogApp.entities.Roles;

public interface RoleRepositories extends JpaRepository<Roles, Integer>{

}
