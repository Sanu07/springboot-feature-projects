package com.test.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
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
import com.test.repository.NRITestRepo;
import com.test.service.NRITestService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("nri-data")
public class NRITestController {

	@Autowired
	NRITestService service;
	
	@Autowired
	NRITestRepo repo;
	
	@Autowired
	private R2dbcEntityTemplate template;

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
	
	@GetMapping("getStatus1")
	public int getStatusData() {
		// return status.block().size();
		return 0;
	}
	
	@GetMapping("getStatus2")
	public Mono getStatusData2() {
		NRI nri2 = NRI.builder().name("test-name2").build();
		repo.save(nri2);
		String name = null;
		List<Integer> list = Arrays.asList(1, 2, 3);
		return Flux.fromIterable(list)
				.doOnComplete(() -> {
					String name2 = "";
					NRI nri3 = NRI.builder().name(name).build();
					testMethod().subscribe(s -> {
						NRI nri = NRI.builder().name(name2).build();
						// name2 = "hello";
						repo.save(nri3);
					});
				})
				.then(Mono.just(list));
	}
	
	@GetMapping("save1")
	public Mono<NRI> saveNRI() {
		NRI nri = NRI.builder().id(1L).name("test-name2").build();
		return repo.save(nri);
	}
	
	@GetMapping("save2")
	public Mono<Long> saveNRI2() {
		return service.saveNRI(NRI.builder().id(3L).name("test-name2").build());
	}
	
	@GetMapping("save3")
	public Mono<NRI> saveNRI3() {
		NRI nri = NRI.builder().id(3L).name("test-name2").build();
		return template.insert(nri);
	}
	
	@GetMapping("getStatus3")
	public Mono<NRIVendorResponse> getStatus2() throws InterruptedException, ExecutionException {
		Mono<NRI> mono1 = Mono.just(NRI.builder().id(1L).name("test-name1").build());
		Mono<NRI> mono2 = Mono.just(NRI.builder().id(2L).name("test-name2").build());
		Mono<NRI> mono3 = Mono.just(NRI.builder().id(3L).name("test-name3").build());
		Flux<NRI> res = Flux.empty();
		Mono<NRI> res2 = Mono.empty();
		Mono<List<NRI>> res3 = Mono.empty();
		List<Mono<NRI>> l1 = new ArrayList<>();
		List<NRI> l2 = new ArrayList<>();
		List<Long> list = Arrays.asList(1L, 2L, 3L);
		Flux<NRI> concat = Flux.concat(mono1, mono2, mono3);
		list.forEach(v -> {
				res2.mergeWith(test2(v));
		});
		res = Flux.fromIterable(l1).flatMapSequential(Function.identity());
		return Mono.just(NRIVendorResponse.builder().body(res.collectList().toFuture().get()).response("SUCCESS").build());
	}
	
	private Mono<NRI> test2(long i) {
		return Mono.just(NRI.builder().id(i).name("test-name" + i).build());
	}
	
	private Mono<Double> testMethod() {
		Mono<Double> progress = service.getStatus()
				.collectList().map(status -> {
					double value = status.stream().map(s -> {
						int count = 0;
						if (s.getField().contains("1")) {
							count++;
						}
						if (s.getValue().contains("1")) {
							count++;
						}
						return count;
					}).reduce(0, (a, b) -> a + b).doubleValue();
					double totalSize = status.size();
					return value / totalSize;
				});
		
		return progress.map(Double::doubleValue);
	}
	
	private List<Status> testMethod2() {
		return service.getStatus().collectList().map(status -> {
			return status;
		}).subscribeOn(Schedulers.immediate()).block();
	}
	
	@GetMapping("mono-boolean")
	public String getNRData() throws InterruptedException, ExecutionException {
		String s = service.getNRIData2();
		if (s.contains("Not")) {
			return "Data is not present";
		} else {
			return "Data is present";
		}
	}
}
