package com.batch.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID>{

}
