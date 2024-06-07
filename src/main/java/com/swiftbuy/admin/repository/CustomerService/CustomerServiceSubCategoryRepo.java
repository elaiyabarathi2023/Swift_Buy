package com.swiftbuy.admin.repository.CustomerService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.swiftbuy.admin.model.CustomerService.CustomerServiceSubCategory;
 
@Repository
public interface CustomerServiceSubCategoryRepo extends CrudRepository<CustomerServiceSubCategory, Long> {
    // You can add custom query methods here if needed
}