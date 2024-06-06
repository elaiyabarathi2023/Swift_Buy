package com.swiftbuy.admin.repository.CustomerServiceSubCategory;
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface CustomerServiceSubCategoryRepo extends CrudRepository<CustomerServiceSubCategory, Long> {
    // You can add custom query methods here if needed
}