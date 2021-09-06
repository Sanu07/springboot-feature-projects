package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.Authorities;


public interface AuthoritiesRepo extends JpaRepository<Authorities, Long> {

	Authorities findByUsername(String username);

}
