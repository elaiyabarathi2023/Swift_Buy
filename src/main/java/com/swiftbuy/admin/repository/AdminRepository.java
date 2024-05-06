package com.swiftbuy.admin.repository;

import com.swiftbuy.admin.model.AdminDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<AdminDetails, Long> {
    AdminDetails findByUsername(String username);

	AdminDetails findByEmail(String username);
}
