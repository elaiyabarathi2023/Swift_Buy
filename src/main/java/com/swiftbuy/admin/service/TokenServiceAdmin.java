//package com.swiftbuy.admin.service;
//
//import java.util.Date;
//
//import javax.crypto.SecretKey;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.swiftbuy.admin.model.AdminDetails;
//import com.swiftbuy.admin.repository.AdminRepository;
//import com.swiftbuy.user.model.UserDetails;
//import com.swiftbuy.user.repository.UserRepository;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//@Service
//public class TokenServiceAdmin {
//	@Autowired
//	AdminRepository adminRepository;
//	private SecretKey getSigningKey() throws Exception {
//		byte[] keyBytes = Decoders.BASE64.decode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
//	
//	public Claims verifyToken(String token) throws Exception {
////	    if (token == null || token.isEmpty()) {
////	        return null; 
////	    }
//
//	    Claims claims;
////	    try {
//	    claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
////	    } catch (InvalidKeyException e) {
////	        throw new InvalidKeyException("Invalid signing key for token verification");
////	    } catch (Exception e) {
////	        throw new Exception("Error parsing token: " + e.getMessage());
////	    }
//
//	    String username = claims.get("username").toString();
//	    String phoneNumber=claims.get("phoneNumber").toString();
//	    AdminDetails user = adminRepository.findByUsername(username);
//	    if (user == null || isTokenExpired(claims)) { // Check user existence and expiration
//	        return null;
//	    }
//	    return claims; // Return claims only if valid
//	}
//
//	private boolean isTokenExpired(Claims claims) {
//	    Date expiration = claims.getExpiration();
//	    return expiration.before(new Date());
//	}
//
//
//}
