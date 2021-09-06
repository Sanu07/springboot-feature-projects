package com.test.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.entity.NRI;
import com.test.entity.NRIVendorResponse;
import com.test.entity.Status;
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

	@PutMapping("date/{date}")
	public void updateMilestoneData(@PathVariable String date) {
		service.update(date);
	}

	@GetMapping("resetIds")
	public Mono<ResponseEntity<NRIVendorResponse>> getNRIResetsData() {
		return service.getResetData().collectList().map(resetIds -> {
			return new ResponseEntity<>(new NRIVendorResponse("SUCCESS", Collections.singletonMap("resetIds", resetIds), null), HttpStatus.OK);
		}).onErrorResume(error -> {
			return Mono.just(new ResponseEntity<>(
					new NRIVendorResponse("FAILURE", "", error.getMessage()), HttpStatus.BAD_REQUEST));
		});
	}
	
	@GetMapping("concatenatedString")
	public Mono<List<String>> getConcatenatedString() {
		return Flux.just("WEQEW", "QWEQW").collectList().map(str -> {
			return str;
		});
	}
	
	@GetMapping("getStatus")
	public Flux<Status> getStatusData() {
		return service.getStatus();
	}
}
