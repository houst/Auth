package com.site.auth.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.site.auth.entity.Role;
import com.site.auth.entity.User;
import com.site.auth.repository.UserRepository;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
	public String userList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "page/user-list";
	}
	
	@GetMapping("{user}")
	public String userEdit(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("roles", Role.values());
		return "page/user-edit";
	}
	
	@PostMapping
	public String userUpdate(
			@RequestParam String email,
			@RequestParam String name,
			@RequestParam String number,
			@RequestParam Map<String, String> form,
			@RequestParam("userId") User user) 
	{
		
		user.setEmail(email);
		user.setUsername(name);
		user.setTel(number);
		
		Set<String> roles = Arrays.stream(Role.values())
				.map(Role::name).collect(Collectors.toSet());
		
		user.getAuthorities().clear();
		
		for (String key : form.keySet()) {
			if (roles.contains(key)) {
				user.getAuthorities().add(Role.valueOf(key));
			}
		}
		
		userRepository.save(user);
		
		return "redirect:/user";
	}
	
}
