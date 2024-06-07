package com.swiftbuy.admin.repository;
 
 
import java.util.Optional;
 
import org.springframework.data.repository.CrudRepository;
 
import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.model.SubCategory;
 
 
public interface CategoryRepository extends CrudRepository<Category,Long> {
 
	Optional<SubCategory> findByName(String categoryName);
 
	
 
}