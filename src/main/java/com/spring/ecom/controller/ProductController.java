
package com.spring.ecom.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Product;
import com.spring.ecom.request.CreateProductRequest;
import com.spring.ecom.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 🔍 Get product by ID

    @GetMapping("/products/{id}") // ✅ Matches frontend
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ProductException {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    // 📦 Get all products with filters
    @GetMapping("/products") // ✅ Matches frontend
    public Page<Product> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(defaultValue = "0") Integer minPrice,
            @RequestParam(defaultValue = "10000") Integer maxPrice,
            @RequestParam(defaultValue = "0") Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return productService.getAllProduct(category, color, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
    }

    // 🛠️ Create new product
    @PostMapping("/admin/products") // ✅ Matches frontend
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) throws IOException {
        Product product = productService.createProduct(req);
        return ResponseEntity.ok(product);
    }

    // 🗑️ Delete product
   
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ProductException {
        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(message);
    }


    // ✏️ Update product quantity (optional if needed)
    @PutMapping("/admin/products/{id}/update")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product req) throws ProductException {
        Product updated = productService.updateProduct(id, req);
        return ResponseEntity.ok(updated); 
    }
}
