package com.spring.ecom.service;

import java.util.List;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Rating;
import com.spring.ecom.model.User;
import com.spring.ecom.request.RatingRequest;




public interface RatingService {
	public Rating createRating(RatingRequest req, User user) throws ProductException;
	public List<Rating>getProductRating(Long productId);
	

}
//service