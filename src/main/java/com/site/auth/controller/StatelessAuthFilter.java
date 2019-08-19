package com.site.auth.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.site.auth.service.TokenAuthService;

import lombok.NonNull;

public class StatelessAuthFilter extends GenericFilterBean {
	
	private TokenAuthService tokenAuthService;
	
	public StatelessAuthFilter(@NonNull TokenAuthService tokenAuthService) {
		this.tokenAuthService = tokenAuthService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(
				tokenAuthService.getAuthentication((HttpServletRequest) request).orElse(null));
		
		chain.doFilter(request, response);
	}
	
}
