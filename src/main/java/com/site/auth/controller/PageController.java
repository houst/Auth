package com.site.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.site.auth.entity.User;

@Controller
public class PageController {
	
	@GetMapping("/") 
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try { 
			model.addAttribute("username", ((User) auth.getPrincipal()).getUsername());
		} catch (Exception e) {}
		return "index"; 
	}
	
}
