
package com.spring.ecom.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Product;
import com.spring.ecom.request.CreateProductRequest;
import com.spring.ecom.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<Page<Product> >getAllProducts(
            @RequestParam String category,
            @RequestParam List<String> color,
            @RequestParam Set<String> size,
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice,
            @RequestParam Integer minDiscount,
            @RequestParam String sort,
            @RequestParam String stock,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
            
    ) {
     Page<Product>res= productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
   System.out.println("complet products");
   return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
   }

    // 🛠️ Create new product
    @PostMapping("/admin/products") // ✅ Matches frontend
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) throws IOException {
        Product product = productService.createProduct(req);
        return ResponseEntity.ok(product);
    }

    // 🗑️ Delete product
   
    @DeleteMapping("/admin/products/{id}/delete")
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
