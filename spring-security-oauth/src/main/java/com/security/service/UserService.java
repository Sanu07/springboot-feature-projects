package com.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.model.User;
import com.security.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	public User register(User user) {
		return userRepo.save(user);
	}
	
	public User getDetails(Long id) {
		return userRepo.getById(id);
	}
	
	public List<User> getUsers() {
		return userRepo.findAll();
	}
}
