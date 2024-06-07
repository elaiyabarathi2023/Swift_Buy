package com.swiftbuy.admin.repository;
 
//import org.springframework.data.repository.CrudRepository;
//
//import com.swiftbuy.admin.model.SubCategory;
//
//public interface SubCategoryRepository extends CrudRepository<SubCategory,Long> {
//
//}
 
 
import com.swiftbuy.admin.model.SubCategory;
 
import java.util.Optional;
 
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {
 
	Optional<SubCategory> findByName(String subcategoryName);
    // Additional custom methods can be added here if needed
}