package com.swiftbuy.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class FilterConfigAdmin {
	@Bean
	public FilterRegistrationBean jwtFilterAdmin() {
		FilterRegistrationBean filter = new FilterRegistrationBean();

//	//	filter.setFilter(new jwtFilterAdmin());
		filter.setFilter(new jwtFilterAdmin());
		filter.addUrlPatterns("/admin/productpart/*");
//
//		filter.addUrlPatterns("/api/wishlist/add*");

		return filter;
	}
}


