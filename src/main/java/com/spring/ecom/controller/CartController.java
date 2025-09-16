package com.spring.ecom.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.exception.UserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.spring.ecom.model.Cart;
import com.spring.ecom.model.CartItem;
import com.spring.ecom.model.User;
import com.spring.ecom.repository.CartRepository;
import com.spring.ecom.request.AddItemRequest;
import com.spring.ecom.response.AppResponse;
import com.spring.ecom.service.CartService;
import com.spring.ecom.service.UserService;

import org.springframework.http.ResponseEntity;





@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCartByJwt(@RequestHeader("Authorization") String jwt) throws UserException {
        // Remove "Bearer " prefix if present
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/test") //check cart working or not
    public ResponseEntity<String> getCartTest() {
        return ResponseEntity.ok("Cart is working!");
    }
    
   
    
    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<AppResponse> addItemCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt) 
            throws UserException, ProductException {

        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);

        AppResponse res = new AppResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);

        return ResponseEntity.ok(res); // ✅ clean and correct
    }
    @GetMapping("/items")
    public ResponseEntity<Set<CartItem>> getCartItems(@RequestHeader("Authorization") String jwt) throws UserException {
        if (jwt.startsWith("Bearer ")) jwt = jwt.substring(7);
        User user = userService.findUserProfileByJwt(jwt);
        Set<CartItem> items = cartService.getCartItems(user.getId());
        return ResponseEntity.ok(items);
    }


}
