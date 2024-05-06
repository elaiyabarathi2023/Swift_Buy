package com.swiftbuy.subcrepository;

//import org.springframework.data.repository.CrudRepository;
//
//import com.swiftbuy.admin.model.SubCategory;
//
//public interface SubCategoryRepository extends CrudRepository<SubCategory,Long> {
//
//}


import com.swiftbuy.admin.model.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {
    // Additional custom methods can be added here if needed
}
