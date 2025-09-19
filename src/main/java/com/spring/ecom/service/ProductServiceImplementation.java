package com.spring.ecom.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.IOException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.ecom.exception.ProductException;
import com.spring.ecom.model.Category;
import com.spring.ecom.model.Product;
import com.spring.ecom.repository.CategoryRepository;
import com.spring.ecom.repository.ProductRepository;
import com.spring.ecom.request.CreateProductRequest;



@Service
public class ProductServiceImplementation implements ProductService {
	private  ProductRepository productRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;
	public ProductServiceImplementation(ProductRepository productRepository, UserService userService,CategoryRepository categoryRepository) {
		this.productRepository=productRepository;
		this.userService=userService;
		this.categoryRepository=categoryRepository;
		
	}

	@Override
	public Product createProduct(CreateProductRequest req) throws IOException {

	    // ------- CATEGORY CREATION LOGIC --------
	
		// Top Level
		List<Category> topList = categoryRepository.findAllByName(req.getTopLevelCategory());
		Category topLevel;
		if (topList.isEmpty()) {
		    topLevel = new Category();
		    topLevel.setName(req.getTopLevelCategory());
		    topLevel.setLevel(1);
		    topLevel = categoryRepository.save(topLevel);
		} else {
		    topLevel = topList.get(0); // agar multiple result ho, first use karo
		}

		// Second Level
		List<Category> secondList = categoryRepository.findAllByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
		Category secondLevel;
		if (secondList.isEmpty()) {
		    secondLevel = new Category();
		    secondLevel.setName(req.getSecondLevelCategory());
		    secondLevel.setParentCategory(topLevel);
		    secondLevel.setLevel(2);
		    secondLevel = categoryRepository.save(secondLevel);
		} else {
		    secondLevel = secondList.get(0);
		}

		// Third Level
		List<Category> thirdList = categoryRepository.findAllByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
		Category thirdLevel;
		if (thirdList.isEmpty()) {
		    thirdLevel = new Category();
		    thirdLevel.setName(req.getThirdLevelCategory());
		    thirdLevel.setParentCategory(secondLevel);
		    thirdLevel.setLevel(3);
		    thirdLevel = categoryRepository.save(thirdLevel);
		} else {
		    thirdLevel = thirdList.get(0);
		}
  

	    // ------- PRODUCT CREATION --------
		 Product product = new Product();
		    
		    product.setTitle(req.getTitle());
		    product.setColor(req.getColor());
		    product.setDescription(req.getDescription());
		    product.setDiscountedPrice(req.getDiscountedPrice());
		    product.setDiscountedPersent(req.getDiscountedPersent());

		    product.setBrand(req.getBrand());
		    product.setPrice(req.getPrice());
		    
		    // --- (Size mapping logic remains the same) ---
		    // ...
		    
		    product.setQuantity(req.getQuantity());
		    product.setCategory(thirdLevel);
		    product.setCreatedAt(LocalDateTime.now());
		    
		    // 💡 Add this line to set the image URL
		    product.setImageUrl(req.getImageUrl());
		    if (req.getSize() != null && !req.getSize().isEmpty()) {
		        product.setSize(new HashSet<>(req.getSize()));
		    }

		    return productRepository.save(product);
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSize().clear();
		productRepository.delete(product);
		return "Product deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product  = findProductById(productId);
		if(req.getQuantity()!= 0) {
			product.setQuantity(req.getQuantity());
			
		}
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product>opt =productRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found id -"+id);
		
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, Set<String> size, Integer minPrice,
	        Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

	    // ✅ Step 1: Setup sorting
	    Sort sortOrder = Sort.by("discountedPrice");
	    if ("price_high".equalsIgnoreCase(sort)) {
	        sortOrder = sortOrder.descending();
	    } else {
	        sortOrder = sortOrder.ascending();
	    }

	    Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOrder);

	    // ✅ Step 2: Get paginated products from DB
	    Page<Product> productsPage = productRepository.filterProducts(
	        category, minPrice, maxPrice, minDiscount, pageable
	    );

	    // ✅ Step 3: Apply color and stock filters on page content
	    List<Product> filtered = productsPage.getContent();

	    if (!colors.isEmpty()) {
	        filtered = filtered.stream()
	            .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
	            .collect(Collectors.toList());
	    }

	    if (stock != null) {
	        if (stock.equals("in_stock")) {
	            filtered = filtered.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
	        } else if (stock.equals("out_of_stock")) {
	            filtered = filtered.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
	        }
	    }

	    // ✅ Step 4: Return filtered page (no manual slicing needed)
	    return new PageImpl<>(filtered, pageable, productsPage.getTotalElements());
	}

}//service
