package com.swiftbuy.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@Configuration
@SecurityScheme(   name = "Bearer Authentication",   type = SecuritySchemeType.HTTP,   bearerFormat = "JWT",   scheme = "bearer" )
public class FilterConfigUser {
    @Bean
    public FilterRegistrationBean jwtFilterUser() {
        FilterRegistrationBean filter= new FilterRegistrationBean();

        filter.setFilter(new jwtFilterUser());
      filter.addUrlPatterns("/api/shoppingcart/add","/api/shoppingcart/cart","/order","/order/orders/get","/api/wishlist/add");
    

    return filter;
    }
  
}