package com.swiftbuy.admin.repository.CustomerService;
 
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.swiftbuy.admin.model.CustomerService.CustomerServiceCategory;
 
@Repository
public interface CustomerServiceCategoryRepository extends CrudRepository<CustomerServiceCategory, Long> {

	
}