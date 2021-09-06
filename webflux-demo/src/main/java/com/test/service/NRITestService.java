package com.test.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.config.DBConnector;
import com.test.entity.Milestone;
import com.test.entity.NRI;
import com.test.entity.Status;
import com.test.entity.Tracker;
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
		return client.execute("SELECT * FROM NRI").map((row) -> {
			NRI nri = null;
			try {
				nri = NRI.builder().id(row.get("id", Long.class)).trackers(
						mapper.readValue(row.get("trackers", String.class), new TypeReference<List<Tracker>>() {
						})).milestones(mapper.readValue(row.get("milestones", String.class),
								new TypeReference<Map<String, Milestone>>() {
								}))
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
		// return repo.saveData(data.getId(),
		// mapper.writeValueAsString(data.getData()));
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		DatabaseClient client = connector.createConnection();
		client.execute("INSERT INTO NRI (ID, TRACKERS, MILESTONES) VALUES($1, $2::JSON, $3::JSON)")
				.bind("$1", data.getId()).bind("$2", mapper.writeValueAsString(data.getTrackers()))
				.bind("$3", mapper.writeValueAsString(data.getMilestones())).fetch().first().subscribe();
		return Mono.just(data);
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
		return null;
	}
}
