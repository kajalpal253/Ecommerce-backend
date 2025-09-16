package com.spring.ecom.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Cart;
import com.spring.ecom.model.CartItem;
import com.spring.ecom.model.Product;
import com.spring.ecom.model.User;
import com.spring.ecom.repository.CartRepository;
import com.spring.ecom.request.AddItemRequest;


@Service
public class CartServiceImplementation  implements CartService {
	
	private  CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	
	private CartService cartService;
	
	

	public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,
			ProductService productService) {
		this.cartRepository = cartRepository;
		this.cartItemService = cartItemService;
		this.productService = productService;
	}

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
        
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart= cartRepository.findByUserId(userId);
	    Product  product= productService.findProductById(req.getProductId());
	    CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
	    if(isPresent == null) {
	    	CartItem cartItem = new CartItem();
	    	cartItem.setProduct(product);
	    	cartItem.setCart(cart);
	    	cartItem.setQuantity(req.getQuantity());
	    	cartItem.setUserId(userId);
	    	int price =req.getQuantity() * product.getDiscountedPrice();
	    	cartItem.setPrice(price);
	    	cartItem.setSize(req.getSize());
	    	CartItem createdCartItem = cartItemService.createCartItem(cartItem);
	    	cart.getCartItems().add(createdCartItem);
	    	
	    }
	    findUserCart(userId);
	    return  "Item Add To Cart";
	    
	}
	

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
	    if (cart == null) {
	        throw new RuntimeException("Cart not found for userId: " + userId);
	    }
		int totalPrice =0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		for(CartItem cartItem :cart.getCartItems()) {
			totalPrice =totalPrice+ cartItem.getPrice();
			totalDiscountedPrice =totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem =totalItem+cartItem.getQuantity();
			
		}
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalitem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice );
		
		
		return cartRepository.save(cart);
	}
	@Override
	public Set<CartItem> getCartItems(Long userId) {
	    Cart cart = cartRepository.findByUserId(userId);
	    if (cart != null) {
	        return cart.getCartItems(); // Assuming this returns Set<CartItem>
	    }
	    return new HashSet<>(); // ✅ Return an empty Set instead of ArrayList
	}
	

}
//service