package com.spring.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ecom.exception.OrderException;
import com.spring.ecom.exception.UserException;
import com.spring.ecom.model.Address;
import com.spring.ecom.model.Order;
import com.spring.ecom.model.User;
import com.spring.ecom.service.OrderService;
import com.spring.ecom.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;  // ✅ Use this!




@RestController
@RequestMapping("/api/orders")
public class OrderControll {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private  UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order>createOrder(@RequestHeader("Authorization") String jwt,@RequestBody Address shippingAddress) throws UserException{
		
		if(jwt.startsWith("Bearer")) {
			jwt=jwt.substring(7);
		}
		
		User use = userService.findUserProfileByJwt(jwt);

		Order order =orderService.createOrder(use, shippingAddress);

		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>>userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
		if(jwt.startsWith("Bearer")) {
			jwt = jwt.substring(7);
		}
		User user = userService.findUserProfileByJwt(jwt);
		List<Order>orders =orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders,HttpStatus.CREATED);
		
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrderById(
	    @PathVariable("id") Long orderId,
	    @RequestHeader("Authorization") String jwt
	) throws OrderException, UserException {

	    if (jwt == null || !jwt.startsWith("Bearer ")) {
	        throw new UserException("Invalid or missing Authorization header");
	    }

	    jwt = jwt.substring(7);
	    User user = userService.findUserProfileByJwt(jwt);
	    Order order = orderService.findOrderById(orderId);

	    if (!order.getUser().getId().equals(user.getId())) {
	        throw new OrderException("Unauthorized access to order");
	    }

	    return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException {
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user =userService.findUserProfileByJwt(jwt);
		 orderService.deleteOrder(orderId);
	
	}
	@PutMapping("/{orderId}/placed")
	public ResponseEntity<Order> placedOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user = userService.findUserProfileByJwt(jwt);
		 Order order=orderService.placedOrder(orderId);
		 return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	@PostMapping("/{orderId}/confirmed")
	public  ResponseEntity<Order> confirmedOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user = userService.findUserProfileByJwt(jwt);
		
		 Order order=orderService.confirmedOrder(orderId);
		 return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/cancle")
	public ResponseEntity<Order> cancleOderOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user = userService.findUserProfileByJwt(jwt);
		 Order order=orderService.cancleOderOrder(orderId);
		 return new ResponseEntity<>(order, HttpStatus.OK);
	}
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order>shippeddOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user = userService.findUserProfileByJwt(jwt);
		 Order order=orderService.shippeddOrder(orderId);
		 return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> delivereOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user = userService.findUserProfileByJwt(jwt);
		 Order order=orderService.delivereOrder(orderId);
		 return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/delete")
	public void AdmindeleteOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException {
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user =userService.findUserProfileByJwt(jwt);
		 orderService.deleteOrder(orderId);
	
	}
	
	@GetMapping("/")
	public  ResponseEntity<List<Order>> getAllOrders()  {
		
		List<Order> order=orderService.getAllOrders();
		 return new ResponseEntity<>(order, HttpStatus.OK);
	
	}
	
}// they control order 
