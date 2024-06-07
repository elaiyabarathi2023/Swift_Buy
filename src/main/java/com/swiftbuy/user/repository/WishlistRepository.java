package com.swiftbuy.user.repository;
 
import com.swiftbuy.user.model.WishList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
 
@Repository
public interface WishlistRepository extends CrudRepository<WishList, Long> {
    List<WishList> findByUserId(Long userId);
}