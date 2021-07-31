package com.test.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.NRI;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface NRITestRepo extends ReactiveCrudRepository<NRI, Long> {

	@Query("INSERT INTO NRI (ID, DATA) VALUES ($1, $2::json)")
	Mono<NRI> saveData(Long id, String data);
	
	@Query("SELECT NRI.* FROM NRI")
	Flux<NRI> getData();
}
