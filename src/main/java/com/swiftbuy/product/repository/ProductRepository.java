package com.swiftbuy.product.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.model.ProductDetails.ProductStatus;

public interface ProductRepository
		extends CrudRepository<ProductDetails, Long>, PagingAndSortingRepository<ProductDetails, Long> {
	Page<ProductDetails> findAll(Pageable pageable);

	List<ProductDetails> findByProductStatus(ProductStatus active);
}
