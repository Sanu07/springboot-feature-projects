package com.test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.config.DBConnector;
import com.test.entity.NRI;
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
		return client.execute("SELECT * FROM NRI")
			.map((row) -> {
				NRI nri = null;
				try {
					nri = NRI.builder()
							.id(row.get("id", Long.class))
							.trackers(mapper.readValue(row.get("trackers", String.class), List.class))
							.milestones(mapper.readValue(row.get("milestones", String.class), Map.class))
							.build();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return nri;
			}).all();
	}

	@SuppressWarnings("deprecation")
	public Mono<NRI> save(NRI data) throws JsonProcessingException {
		// client.execute("").fetch().;
		// client.execute("");
		// return repo.saveData(data.getId(), mapper.writeValueAsString(data.getData()));
		DatabaseClient client = connector.createConnection();
		client.execute("INSERT INTO NRI (ID, TRACKERS, MILESTONES) VALUES($1, $2::JSON, $3::JSON)")
			.bind("$1", data.getId())
			.bind("$2", mapper.writeValueAsString(data.getTrackers()))
			.bind("$3", mapper.writeValueAsString(data.getMilestones()))
			.fetch().first().subscribe();
		return Mono.just(data);
	}
}
