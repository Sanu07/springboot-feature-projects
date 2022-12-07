package com.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.config.DBConnector;
import com.test.entity.FakeJSON;
import com.test.entity.Milestone;
import com.test.entity.NRI;
import com.test.entity.Status;
import com.test.entity.Tracker;
import com.test.exception.MyException;
import com.test.repository.NRITestRepo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class NRITestService {

	@Autowired
	NRITestRepo repo;

	@Autowired
	R2dbcEntityTemplate template;

	@Autowired
	DBConnector connector;

	@Autowired
	ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("deprecation")
	public Flux<NRI> getData() {
		DatabaseClient client = connector.createConnection();
//		return client.execute("SELECT * FROM NRI").map((row) -> {
//			NRI nri = null;
//			try {
//				nri = NRI.builder().id(row.get("id", Long.class)).trackers(
//						mapper.readValue(row.get("trackers", String.class), new TypeReference<List<Tracker>>() {
//						})).milestones(mapper.readValue(row.get("milestones", String.class),
//								new TypeReference<Map<String, Milestone>>() {
//								}))
//						.build();
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//			return nri;
//		}).all();
		return null;
	}

	@SuppressWarnings("deprecation")
	public Mono<NRI> save(NRI data) throws JsonProcessingException {
		// client.execute("").fetch().;
		// client.execute("");
		// return repo.saveData(data.getId(),
		// mapper.writeValueAsString(data.getData()));
		
		
//		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
//				.withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE)
//				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
//				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
//		DatabaseClient client = connector.createConnection();
//		client.execute("INSERT INTO NRI (ID, TRACKERS, MILESTONES) VALUES($1, $2::JSON, $3::JSON)")
//				.bind("$1", data.getId()).bind("$2", mapper.writeValueAsString(data.getTrackers()))
//				.bind("$3", mapper.writeValueAsString(data.getMilestones())).fetch().first().subscribe();
//		return Mono.just(data);
		return null;
	}

	public void update(String date) {
		DatabaseClient client = connector.createConnection();
		client.execute(
				"UPDATE NRI SET MILESTONES = JSONB_SET(MILESTONES::JSONB, '{milestone1, date1}', concat('\"',$1,'\"')::jsonb) WHERE ID = 2")
				.bind("$1", date).fetch().first().subscribe();
	}

	public Flux<Long> getResetData() {
		return Flux.just(1L, 2L, 3L);
	}

	public Flux<Status> getStatus() {
		return Flux.just(Status.builder().field("F1").value("V1").build(),
				Status.builder().field("F2").value("V2").build(),
				Status.builder().field("F11").value("V11").build());
	}
	
	public Mono<Long> saveNRI(NRI nri) {
		DatabaseClient client = connector.createConnection();
		return client.insert().into(NRI.class)
                .using(nri)
                .fetch()
                .one()
                .map(m ->(Long) m.get("id"));
	}
	
	public Flux<NRI> getNRIData() {
		return Flux.just(NRI.builder().id(1L).name("test-name1").build(),
				NRI.builder().id(2L).name("test-name2").build(),
				NRI.builder().id(3L).name("test-name3").build(),
				NRI.builder().id(4L).name("test-name4").build(),
				NRI.builder().id(5L).name("test-name5").build());
	}
	
	public String getNRIData2() throws InterruptedException, ExecutionException {
		Flux<NRI> nris = repo.findAll();
		List<NRI> list = nris.collectList().toFuture().get();
		System.out.println(list);
		boolean anyMatch = list.stream().anyMatch(n -> n.getId() % 5 == 0);
		if (anyMatch) return "Present";
		else return "Not present";
		
	}

	public Mono<FakeJSON> getPostmanCode() {
		return getPostmanCodeData(FakeJSON.class);
	}
	
	private <K, T> Mono<K> getPostmanCodeData(Class<K> cls) {
		//return Mono.error(new MyException("error received from service postman", HttpStatus.BAD_REQUEST));
		return WebClient.create("https://jsonplaceholder.typicode.com")
		.get()
		.uri("todos/1")
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.retrieve()
		.onStatus(HttpStatus::is2xxSuccessful, response -> {
            return response.bodyToMono(String.class).flatMap(error -> {
                return Mono.error(new MyException(error));
            });
        }).bodyToMono(cls);
	}
}
