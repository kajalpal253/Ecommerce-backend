package com.spring.ecom.service;

import com.spring.ecom.exception.UserException;
import com.spring.ecom.model.User;

public interface UserService {
	
public User findUserById(Long userId)throws UserException;

public User findUserProfileByJwt(String jwt) throws UserException;




}
//service