package com.site.auth.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.site.auth.controller.TokenHandler;
import com.site.auth.controller.UserAuthentication;

import lombok.NonNull;

@Service
public class TokenAuthService {
	
	@Autowired
	TokenHandler tokenHandler;
	
	@Autowired
	UserService userService;
	
	private static final String AUTH_HEADER_NAME = "X-Auth-Token";

	public Optional<Authentication> getAuthentication(@NonNull HttpServletRequest request) {
		return Optional
				.ofNullable(request.getHeader(AUTH_HEADER_NAME))
				.flatMap(tokenHandler::extractUserId)
				.flatMap(userService::findById)
				.map(UserAuthentication::new);
	}
	
	
	
}
