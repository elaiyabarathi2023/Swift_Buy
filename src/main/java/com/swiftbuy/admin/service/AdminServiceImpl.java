package com.swiftbuy.admin.service;

import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticateAdmin(String username, String password) {
        AdminDetails admin = adminRepository.findByUsername(username);
        if (admin != null) {
            // Check if password matches
            return passwordEncoder.matches(password, admin.getPassword());
        }
        return false;
    }

    @Override
    public AdminDetails getAdminDetails(Long userId) {
        return adminRepository.findById(userId).orElse(null);
    }

    @Override
    public AdminDetails updateAdminProfile(Long userId, AdminDetails updatedAdminDetails) {
        // Check if admin exists
        AdminDetails admin = adminRepository.findById(userId).orElse(null);
        if (admin != null) {
            // Update admin details
            admin.setFirstname(updatedAdminDetails.getFirstname());
            admin.setLastname(updatedAdminDetails.getLastname());
            // Save updated admin details
            return adminRepository.save(admin);
        }
        return null;
    }
}
