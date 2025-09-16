package com.spring.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.ecom.model.OrderItem;



public interface OrderItemRepository  extends JpaRepository<OrderItem, Long>{
	

}
//repository