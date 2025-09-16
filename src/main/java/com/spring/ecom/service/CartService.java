package com.spring.ecom.service;


import java.util.Set;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Cart;
import com.spring.ecom.model.CartItem;
import com.spring.ecom.model.User;
import com.spring.ecom.request.AddItemRequest;




public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
    public Set<CartItem> getCartItems(Long userId); 


 
}
//service