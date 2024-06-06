package com.swiftbuy.admin.repository.CustomerServiceCategory;
 
import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface CustomerServiceCategoryRepository extends CrudRepository<CustomerServiceCategory, Long> {

	
}