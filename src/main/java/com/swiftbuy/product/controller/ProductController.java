package com.swiftbuy.product.controller;

import com.swiftbuy.admin.model.*;
import com.swiftbuy.product.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/productpart")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Product API calls
    
//    @GetMapping("/allproducts")
//    public ResponseEntity<Iterable<ProductDetails>> getAllProductsForAdmin() {
//        Iterable<ProductDetails> products = productService.getAllProducts();
//        return ResponseEntity.ok(products);
//    }
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

    @PutMapping("/products/{productId}/status")
    public ResponseEntity<ProductDetails> updateProductStatus(@PathVariable Long productId, @RequestBody ProductDetails status) {
        ProductDetails updatedProduct = productService.updateProductStatus(productId, status);
        return ResponseEntity.ok(updatedProduct);
    }

    // Product Description API calls
    @PostMapping("/product-descriptions")
    public ResponseEntity<ProductDetails> createProductDescription(@RequestBody ProductDetails productDescription) {
        ProductDetails createdDescription = productService.createProductDescription(productDescription);
        return new ResponseEntity<>(createdDescription, HttpStatus.CREATED);
    }

    @GetMapping("/product-descriptions/{descriptionId}")
    public ResponseEntity<ProductDetails> getProductDescription(@PathVariable Long descriptionId) {
        ProductDetails productDescription = productService.getProductDescription(descriptionId);
        return ResponseEntity.ok(productDescription);
    }

    @PutMapping("/product-descriptions/{descriptionId}")
    public ResponseEntity<ProductDetails> updateProductDescription(@PathVariable Long descriptionId, @RequestBody ProductDetails productDescription) {
        ProductDetails updatedDescription = productService.updateProductDescription(descriptionId, productDescription);
        return ResponseEntity.ok(updatedDescription);
    }

    @DeleteMapping("/product-descriptions/{descriptionId}")
    public ResponseEntity<Void> deleteProductDescription(@PathVariable Long descriptionId) {
        productService.deleteProductDescription(descriptionId);
        return ResponseEntity.noContent().build();
    }

    // Product Image API calls
    @PostMapping("/product-images")
    public ResponseEntity<ProductDetails> createProductImage(@RequestBody ProductDetails productImage) {
        ProductDetails createdImage = productService.createProductImage(productImage);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }

    @GetMapping("/product-images/{imageId}")
    public ResponseEntity<ProductDetails> getProductImage(@PathVariable Long imageId) {
        ProductDetails productImage = productService.getProductImage(imageId);
        return ResponseEntity.ok(productImage);
    }

    @PutMapping("/product-images/{imageId}")
    public ResponseEntity<ProductDetails> updateProductImage(@PathVariable Long imageId, @RequestBody ProductDetails productImage) {
        ProductDetails updatedImage = productService.updateProductImage(imageId, productImage);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/product-images/{imageId}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long imageId) {
        productService.deleteProductImage(imageId);
        return ResponseEntity.noContent().build();
    }

    // Product Quantity API calls
    @PostMapping("/product-quantities")
    public ResponseEntity<ProductDetails> createProductQuantity(@RequestBody ProductDetails productQuantity) {
        ProductDetails createdQuantity = productService.createProductQuantity(productQuantity);
        return new ResponseEntity<>(createdQuantity, HttpStatus.CREATED);
    }

    @GetMapping("/product-quantities/{quantityId}")
    public ResponseEntity<ProductDetails> getProductQuantity(@PathVariable Long quantityId) {
        ProductDetails productQuantity = productService.getProductQuantity(quantityId);
        return ResponseEntity.ok(productQuantity);
    }

    @PutMapping("/product-quantities/{quantityId}")
    public ResponseEntity<ProductDetails> updateProductQuantity(@PathVariable Long quantityId, @RequestBody ProductDetails productQuantity) {
        ProductDetails updatedQuantity = productService.updateProductQuantity(quantityId, productQuantity);
        return ResponseEntity.ok(updatedQuantity);
    }

    @DeleteMapping("/product-quantities/{quantityId}")
    public ResponseEntity<Void> deleteProductQuantity(@PathVariable Long quantityId) {
        productService.deleteProductQuantity(quantityId);
        return ResponseEntity.noContent().build();
    }

    // Estimated Delivery API calls
    @PostMapping("/estimated-deliveries")
    public ResponseEntity<ProductDetails> createEstimatedDelivery(@RequestBody ProductDetails estimatedDelivery) {
    	ProductDetails createdDelivery = productService.createEstimatedDelivery(estimatedDelivery);
        return new ResponseEntity<>(createdDelivery, HttpStatus.CREATED);
    }

    @GetMapping("/estimated-deliveries/{deliveryId}")
    public ResponseEntity<ProductDetails> getEstimatedDelivery(@PathVariable Long deliveryId) {
    	ProductDetails estimatedDelivery = productService.getEstimatedDelivery(deliveryId);
        return ResponseEntity.ok(estimatedDelivery);
    }

    @PutMapping("/estimated-deliveries/{deliveryId}")
    public ResponseEntity<ProductDetails> updateEstimatedDelivery(@PathVariable Long deliveryId, @RequestBody ProductDetails estimatedDelivery) {
    	ProductDetails updatedDelivery = productService.updateEstimatedDelivery(deliveryId, estimatedDelivery);
        return ResponseEntity.ok(updatedDelivery);
    }

    @DeleteMapping("/estimated-deliveries/{deliveryId}")
    public ResponseEntity<Void> deleteEstimatedDelivery(@PathVariable Long deliveryId) {
        productService.deleteEstimatedDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }

  
}
