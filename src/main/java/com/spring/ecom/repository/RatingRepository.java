package com.spring.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.ecom.model.Rating;



public interface RatingRepository extends JpaRepository<Rating,Long> {
@Query("SELECT r from Rating r Where r.product.id = :productId")
	public List<Rating>getAllProductsRating(@Param("productIs") Long productId);
}
//repository