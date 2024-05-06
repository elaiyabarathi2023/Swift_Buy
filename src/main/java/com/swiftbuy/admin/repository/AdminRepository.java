package com.swiftbuy.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.admin.model.AdminDetails;

public interface AdminRepository extends CrudRepository<AdminDetails, Long>{

	

	AdminDetails findByUsername(String username);

}
