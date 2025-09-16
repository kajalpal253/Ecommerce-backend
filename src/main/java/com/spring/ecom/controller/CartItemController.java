package com.spring.ecom.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.spring.ecom.exception.CartItemException;
import com.spring.ecom.exception.UserException;
import com.spring.ecom.model.CartItem;
import com.spring.ecom.model.User;
import com.spring.ecom.repository.CartItemRepository;
import com.spring.ecom.response.AppResponse;
import com.spring.ecom.service.CartItemService;
import com.spring.ecom.service.CartService;
import com.spring.ecom.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CartItemService cartItemService;
    
    @Autowired
    private CartItemRepository cartItemRepository;


   //cart all function remove ,add ,delete,update
  
    
    @DeleteMapping("/cart_items/{cartItemId}")
    @Operation(description = "Remove cartItem from Cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    public ResponseEntity<AppResponse> deleteCartItem( 
            @PathVariable("cartItemId") Long cartItemId,
            @RequestHeader("Authorization") String jwt
    ) throws CartItemException, UserException {
    	 if (jwt.startsWith("Bearer ")) {
             jwt = jwt.substring(7);
         }
        
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        AppResponse res = new AppResponse();
        res.setMessage("Item deleted from cart");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
    @PutMapping("/{cartItemId}")
    @Operation(description ="Update Item to Cart")
    public ResponseEntity<CartItem> updateCartItem(
    		@RequestBody CartItem cartItem,
    		@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException,CartItemException{
    	 if (jwt.startsWith("Bearer ")) {
             jwt = jwt.substring(7);
         }
    	
    	User user = userService.findUserProfileByJwt(jwt);
    	CartItem updateCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
    	return new ResponseEntity<>(updateCartItem,HttpStatus.OK);
    	
    }
    


}
