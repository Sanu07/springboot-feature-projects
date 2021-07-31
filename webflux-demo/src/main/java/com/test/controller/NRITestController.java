package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.entity.NRI;
import com.test.service.NRITestService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("nri-data")
public class NRITestController {

	@Autowired
	NRITestService service;

	@GetMapping
	public Flux<NRI> getData() {
		Flux<NRI> data = service.getData();
		data.subscribe(d -> {
			log.info("" + d);
		});
		return data;
	}

	@PostMapping
	public Mono<NRI> saveData(@RequestBody NRI data) throws JsonProcessingException {
		return service.save(data);
	}
}
