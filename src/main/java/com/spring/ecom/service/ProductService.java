package com.spring.ecom.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Product;
import com.spring.ecom.request.CreateProductRequest;




public interface ProductService {
	
	Product createProduct(CreateProductRequest req ) throws IOException;

	public String deleteProduct(Long productId)throws ProductException;
	public Product updateProduct(Long productId,Product req)throws ProductException;
	public Product findProductById(Long id) throws ProductException;
	public List<Product>findProductByCategory(String category);
	
	public Page<Product>getAllProduct(String category,List<String> colors,Set<String>size,Integer minPrice, Integer maxPrice,
			Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);

}
//service