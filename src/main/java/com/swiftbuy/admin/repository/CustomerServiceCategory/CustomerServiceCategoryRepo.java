package com.swiftbuy.admin.repository.CustomerServiceCategory;

//public class CustomerServiceCategoryRepo {
//
//}



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;

@Repository
public interface CustomerServiceCategoryRepo extends CrudRepository<CustomerServiceCategory, Long> {
    
}