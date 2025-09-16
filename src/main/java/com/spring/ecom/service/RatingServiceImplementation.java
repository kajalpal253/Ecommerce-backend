package com.spring.ecom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Product;
import com.spring.ecom.model.Rating;
import com.spring.ecom.model.User;
import com.spring.ecom.repository.RatingRepository;
import com.spring.ecom.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService {
	private RatingRepository ratingRepository;
	private ProductService productService;
	
	

	

	public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
		this.ratingRepository = ratingRepository;
		this.productService = productService;
	}

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
     Product product = productService.findProductById(req.getProductId());
     Rating rating = new Rating();
     rating.setProduct(product);
     rating.setUser(user);
     rating.setRating(req.getRating());
     rating.setCreatedAt(LocalDateTime.now());
     return ratingRepository.save(rating);

	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		
		return ratingRepository.getAllProductsRating(productId);
	}

}//service
