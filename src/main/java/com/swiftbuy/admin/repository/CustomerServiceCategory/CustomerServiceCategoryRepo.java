package com.swiftbuy.admin.repository.CustomerServiceCategory;

//public class CustomerServiceCategoryRepo {
//
//}



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;

@Repository
public interface CustomerServiceCategoryRepo extends JpaRepository<CustomerServiceCategory, Long> {
    CustomerServiceCategory findByName(String name);
}