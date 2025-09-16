package com.spring.ecom.service;

import java.util.List;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Review;
import com.spring.ecom.model.User;
import com.spring.ecom.request.ReviewRequest;





public interface ReviewService {

	
	public Review createReview(ReviewRequest req,User user)throws ProductException;
	public List<Review>getAllReview(Long productId);
}
//service