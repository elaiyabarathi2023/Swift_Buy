package com.swiftbuy.admin.repository.CustomerServiceSubCategory;

import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;

//public class CustomerServiceSubCategoryRepo {
//
//}


import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerServiceSubCategoryRepo extends CrudRepository<CustomerServiceSubCategory, Long> {

	CustomerServiceCategory save(CustomerServiceCategory customerServiceCategory);
    // You can add custom queries here if needed
}