package com.swiftbuy.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;

import com.swiftbuy.admin.service.TokenServiceAdmin;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class jwtFilterAdmin extends GenericFilterBean {

	@Autowired
	private TokenServiceAdmin tokenService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (tokenService == null) {
			SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
		}
		String authHeader = request.getHeader("Authorization");
		String token = "";
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String[] part = authHeader.split(" ");
			token = part[1];
		}
		System.out.println(token);
		try {
			Claims claims = tokenService.verifyToken(token);
			// Assuming the userId is a claim in your JWT
			String userIdString = claims.get("userId", String.class);
			Long userId = Long.valueOf(userIdString);
			request.setAttribute("userId", userId);

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(e.getMessage());
		}
	}
}
