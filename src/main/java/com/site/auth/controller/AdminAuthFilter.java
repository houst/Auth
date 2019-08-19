package com.site.auth.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.site.auth.entity.User;
import com.site.auth.service.UserService;

public class AdminAuthFilter extends GenericFilterBean {
	
	@Autowired
	UserService userService;
	
	User admin;
	
	@PostConstruct
	public void init() {
		admin = (User) userService.loadUserByUsername("admin");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(admin));
		
		chain.doFilter(request, response);
	}
	
}
