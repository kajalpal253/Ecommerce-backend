package com.spring.ecom.service;

import java.util.List;

import com.spring.ecom.exception.OrderException;
import com.spring.ecom.model.Address;
import com.spring.ecom.model.Order;
import com.spring.ecom.model.User;


public interface OrderService {
	
 public Order createOrder(User user,Address shippingAddress);
 public Order findOrderById(Long orderId) throws OrderException;
 public List<Order>usersOrderHistory(Long userId);
 public Order placedOrder(Long orderId) throws OrderException;
 public Order confirmedOrder(Long orderId) throws OrderException;
 public Order shippeddOrder(Long orderId) throws OrderException;
 public Order delivereOrder(Long orderId) throws OrderException;
 public Order cancleOderOrder(Long orderId) throws OrderException;
 public List<Order>getAllOrders();
 public void deleteOrder(Long orderId) throws OrderException;

}
//service