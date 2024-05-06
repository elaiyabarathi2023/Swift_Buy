package com.swiftbuy.user.config;

import java.util.Map;

import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.user.model.UserDetails;

import io.jsonwebtoken.security.InvalidKeyException;

public interface TokenGenerator {
Map<String,String>generateToken(UserDetails user);



}
