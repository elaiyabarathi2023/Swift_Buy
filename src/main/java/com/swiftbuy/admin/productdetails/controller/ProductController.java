package com.swiftbuy.admin.productdetails.controller;

import com.swiftbuy.admin.model.*;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/productpart")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductDetails product) {
        Map<String, Object> response = new HashMap<>();
      
            ProductDetails createdProduct = productService.createProduct(product);
            response.put("status", true);
            response.put("message", "Product created successfully");
           
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
       
            ProductDetails product = productService.getProduct(productId);
            response.put("status", true);
            response.put("message", "Product retrieved successfully");
            response.put("product", product);
            return ResponseEntity.ok(response);
    
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long productId, @RequestBody ProductDetails product) {
        Map<String, Object> response = new HashMap<>();
      
            ProductDetails updatedProduct = productService.updateProduct(productId, product);
            response.put("status", true);
            response.put("message", "Product updated successfully");
            response.put("product", updatedProduct);
            return ResponseEntity.ok(response);
      
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
      
            productService.deleteProduct(productId);
            response.put("status", true);
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        
    }

    @GetMapping("/allproducts")
    public ResponseEntity<Map<String, Object>> getAllProductsForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
     
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDetails> products = productService.getAllProducts(pageable);
            response.put("status", true);
            response.put("message", "All products retrieved successfully");
            response.put("products", products);
            return ResponseEntity.ok(response);
        
    }

    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
      
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDetails> activeProducts = productService.getActiveProducts(pageable);
            response.put("status", true);
            response.put("message", "Active products retrieved successfully");
            response.put("products", activeProducts);
            return ResponseEntity.ok(response);
       
    }

    @GetMapping("/inactive")
    public ResponseEntity<Map<String, Object>> getInactiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
    
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDetails> inactiveProducts = productService.getInactiveProducts(pageable);
            response.put("status", true);
            response.put("message", "Inactive products retrieved successfully");
            response.put("products", inactiveProducts);
            return ResponseEntity.ok(response);
        
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestParam("keyword") String keyword, Pageable pageable) {
        Map<String, Object> response = new HashMap<>();
  
            Page<ProductDetails> products = productService.searchProducts(keyword, pageable);
            response.put("status", true);
            response.put("message", "Products searched successfully");
            response.put("products", products);
            return ResponseEntity.ok(response);
        
    }
}