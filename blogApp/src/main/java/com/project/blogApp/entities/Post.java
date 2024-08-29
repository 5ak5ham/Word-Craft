package com.project.blogApp.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	@Column(name = "post_title" , length = 100, nullable = false)
	private String title;
	
	@Column(name = "post_content", nullable = false, length = 10000)
	private String content;
	
	
	private String imageName;
	
	private List<String> images;
	
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	private User user;
	
	
	@OneToMany(mappedBy = "post" , cascade = CascadeType.ALL )
	private Set<Comment> comments = new HashSet<>();
	

}
