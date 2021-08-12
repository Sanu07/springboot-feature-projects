package com.batch.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.batch.mongo.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
