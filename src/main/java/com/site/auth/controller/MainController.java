package com.site.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.site.auth.entity.Role;
import com.site.auth.entity.User;

@Controller
public class MainController {
	
	@GetMapping("/") 
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			model.addAttribute("username", ((User) auth.getPrincipal()).getUsername());
			model.addAttribute("isAdmin", ((User) auth.getPrincipal()).getAuthorities().contains(Role.ADMIN));
		} catch (ClassCastException e) {
			model.addAttribute("username", "nonAuth");
			model.addAttribute("isAdmin", false);
		}
			return "page/index"; 
	}
	
	@GetMapping("/login")
	public String getLogin(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model) 
	{
		model.addAttribute("error", error != null);
		model.addAttribute("logout", logout != null);
		return "index";
	}
	
}
