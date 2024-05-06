package com.swiftbuy.user.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.swiftbuy.user.model.UserDetails;

@Repository
@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<UserDetails, Long> {
    UserDetails findByEmail(String email);
    UserDetails findByPhoneNumber(String phoneNumber);
    boolean existsByFirstname(String firstname);
    UserDetails findByEmailOrPhoneNumber(String email, String phoneNumber);
	boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
	List<UserDetails>findAll(Pageable pageable);
	boolean existsByEmail(String value);
	boolean existsByPhoneNumber(String value);
	
}