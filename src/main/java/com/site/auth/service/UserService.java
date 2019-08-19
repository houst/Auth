package com.site.auth.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.site.auth.entity.Role;
import com.site.auth.entity.User;
import com.site.auth.repository.UserRepository;

import lombok.NonNull;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	@PostConstruct
	public void init() {
		
		User admin = User.builder()
				.email("admin")
				.password(new BCryptPasswordEncoder().encode("1111"))
				.username("admin")
				.tel("1111111")
				.authorities(ImmutableList.of(Role.ADMIN, Role.USER))
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.build();
		
		repository.save(admin);
		
		User user = User.builder()
				.email("user")
				.password(new BCryptPasswordEncoder().encode("1111"))
				.username("user")
				.tel("2222222")
				.authorities(ImmutableList.of(Role.USER))
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.build();
		
		
		repository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
		return repository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid user Email: " + email));	
	}
	
	public Optional<User> findById(long id) {
		return repository.findById(id);
	}
	
}
