package com.swiftbuy.admin.productdetails.controller;
 
import com.swiftbuy.admin.model.*;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.product.service.ProductService;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/admin/productpart")
public class ProductController {
 
    @Autowired
    private ProductService productService;
 
 
   
    @PostMapping("/products")
    public ResponseEntity<ProductDetails> createProduct(@RequestBody ProductDetails product) {
        ProductDetails createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
 
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetails> getProduct(@PathVariable Long productId) {
        ProductDetails product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }
 
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDetails> updateProduct(@PathVariable Long productId, @RequestBody ProductDetails product) {
        ProductDetails updatedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(updatedProduct);
    }
 
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/allproducts")
    public ResponseEntity<Page<ProductDetails>> getAllProductsForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetails> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }
 
    
    @GetMapping("/active")
    public ResponseEntity<Page<ProductDetails>> getActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetails> activeProducts = productService.getActiveProducts(pageable);
        return ResponseEntity.ok(activeProducts);
    }
 
    @GetMapping("/inactive")
    public ResponseEntity<Page<ProductDetails>> getInactiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetails> inactiveProducts = productService.getInactiveProducts(pageable);
        return ResponseEntity.ok(inactiveProducts);
    }
 
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDetails>> searchProducts(@RequestParam("keyword") String keyword, Pageable pageable) {
        Page<ProductDetails> products = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(products);
    }
}