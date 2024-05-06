package com.swiftbuy.product.repository;



import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.admin.model.ProductDetails;


public interface ProductRepository  extends CrudRepository<ProductDetails, Long> {
	  List<ProductDetails> findAll(Pageable pageable);
	}
