package com.project.blogApp.entities;


import java.util.List;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	

	@Column(name = "user_name", nullable = false, length = 100)
	private String name;

	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String about;
	
	private String imageName;
	
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Post> posts = new HashSet<>();
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();

	
	@ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user" , referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "roles" , referencedColumnName = "id"))
	private Set<Roles> roles = new HashSet<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return authorities;
	}


	@Override
	public String getUsername() {
		return this.email;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}
}