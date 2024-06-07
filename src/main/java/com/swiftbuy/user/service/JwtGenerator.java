package com.swiftbuy.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.user.config.TokenGeneratorAdmin;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtGenerator implements TokenGeneratorAdmin {
	@Autowired
	private UserRepository userRepository;

	
	public JwtGenerator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public Map<String, String> generateToken(UserDetails userdata)throws InvalidKeyException {
		   Map<String, String> jwtTokenGen = new HashMap<>();
		Map<String, String> claims = new LinkedHashMap<>();
		claims.put("firstname", userdata.getFirstname());
		claims.put("userId", userdata.getUserId().toString());
		userdata.setCreatedAt(new Date());
		 if (userdata.getEmail() != null && !userdata.getEmail().isEmpty()) {
		        claims.put("email", userdata.getEmail());
		    }
		 if (userdata.getPhoneNumber() != null && !userdata.getPhoneNumber().isEmpty()) {
		        claims.put("phoneNumber", userdata.getPhoneNumber());
		    }
		
       UserDetails savedUser = userRepository.save(userdata);
        
        Date expiration = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
  		 //String token = null;
  		//try {
			String	token = Jwts.builder().claims(claims).subject(userdata.getUserId().toString()) // Use user ID instead of password
 
						.issuer("theertha")
						.signWith(getSigningKey())
						.issuedAt(new Date()).expiration(expiration)
						.compact();
			//} catch (InvalidKeyException e) {
				
		//	} catch (Exception e) {
			//	e.printStackTrace();
		//	}
		jwtTokenGen.put("status", "true");
  		jwtTokenGen.put("message","Token generated successfully");
  		jwtTokenGen.put("token", token);
	    return jwtTokenGen;
	  
	  }
	 private SecretKey getSigningKey() {
	  	  byte[] keyBytes = Decoders.BASE64.decode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
	  	  return Keys.hmacShaKeyFor(keyBytes);
		}
}