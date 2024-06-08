package com.swiftbuy.admin.product.service;
import com.swiftbuy.admin.model.*;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.repository.CategoryRepository;
import com.swiftbuy.admin.repository.CouponCodeRepository;
import com.swiftbuy.admin.repository.SubCategoryRepository;
 
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
     private CategoryRepository   categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    public ProductDetails createProduct(ProductDetails product) {
        Long subCategoryId = product.getSubcategory().getId();
        Long categoryId = product.getCategory().getCategory_id();
 
        // Retrieve the category and subcategory from their respective repositories
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
 
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id " + subCategoryId));
 
        // Set the retrieved category and subcategory to the product
        product.setCategory(category);
        product.setSubcategory(subCategory);
 
        // Save the product
        return productRepository.save(product);
    }
 

    public ProductDetails getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }

    public ProductDetails updateProduct(Long productId, ProductDetails product) {
        ProductDetails existingProduct = getProduct(productId);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductImage(product.getProductImage());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setProductQuantity(product.getProductQuantity());
        existingProduct.setEstimatedDelivery(product.getEstimatedDelivery());
        return productRepository.save(existingProduct);
    }
    public void deleteProduct(Long productId) {
        ProductDetails product = getProduct(productId);
        productRepository.delete(product);
    }

    public Page<ProductDetails> getActiveProducts(Pageable pageable) {
        return productRepository.findByProductStatus(ProductDetails.ProductStatus.ACTIVE, pageable);
    }
    public Page<ProductDetails> getInactiveProducts(Pageable pageable) {
        return productRepository.findByProductStatus(ProductDetails.ProductStatus.INACTIVE, pageable);
    }
    public Page<ProductDetails> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Page<ProductDetails> searchProducts(String keyword, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
    }
}