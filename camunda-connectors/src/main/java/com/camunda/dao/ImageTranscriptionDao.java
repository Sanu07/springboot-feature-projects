package com.camunda.dao;

import com.camunda.entity.ImageTranscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageTranscriptionDao extends JpaRepository<ImageTranscription, Long> {
}
