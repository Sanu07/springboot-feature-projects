package com.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.model.Authorities;
import com.security.model.User;
import com.security.repo.AuthoritiesRepo;
import com.security.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuthoritiesRepo authRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isPresent()) {
			User u = user.get();
			return org.springframework.security.core.userdetails.User.builder()
					.authorities(u.getAuth().getRole())
					.credentialsExpired(false)
					.password(u.getPassword())
					.username(u.getUsername())
					.build();
		} else {
			throw new UsernameNotFoundException("User Name is not Found");
		}
	}
	
}
