package com.batch.mongo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.batch.mongo.model.Feedback;


@Repository(value = "mongoFeedbackRepository")
public interface FeedbackRepository extends MongoRepository<Feedback, String>{

}
