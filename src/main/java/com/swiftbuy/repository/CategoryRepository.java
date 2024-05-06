package com.swiftbuy.repository;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.admin.model.Category;

public interface CategoryRepository extends CrudRepository<Category,Long> {

}
