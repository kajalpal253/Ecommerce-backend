package com.spring.ecom.service;

import com.spring.ecom.exception.CartItemException;
import com.spring.ecom.exception.UserException;
import com.spring.ecom.model.Cart;
import com.spring.ecom.model.CartItem;
import com.spring.ecom.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem  cartItem);
	
	 public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) 
	            throws CartItemException, UserException;	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public  void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;

	

}
//service
	
