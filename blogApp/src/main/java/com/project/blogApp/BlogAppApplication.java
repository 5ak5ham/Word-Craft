package com.project.blogApp;



import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.blogApp.config.AppConstants;
import com.project.blogApp.entities.Roles;
import com.project.blogApp.repositories.RoleRepositories;



@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{

	
	@Autowired
	private RoleRepositories roleRepositories;
	//@Autowired
	//private DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		
		try {
			Roles role = new Roles();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Roles role2 = new Roles();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");
			
			List<Roles> roles = List.of(role, role2);
			List<Roles> result = this.roleRepositories.saveAll(roles);
			
			result.forEach(r -> {
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			
		}
		
	}
	

	/*@Bean
	
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPackagesToScan("com.project.blogApp");
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		return entityManagerFactoryBean;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabasePlatform("${spring.jpa.properties.hibernate.dialect}");
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}*/
}