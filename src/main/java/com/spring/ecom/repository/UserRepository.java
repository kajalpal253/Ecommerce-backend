package com.spring.ecom.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.ecom.model.User;



public interface UserRepository extends JpaRepository<User,Long> {
	
	
	public User findByEmail(String email); 
	

}
//repository