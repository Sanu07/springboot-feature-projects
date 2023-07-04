package com.camunda.producers;

import com.camunda.entity.ImageTranscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private ObjectMapper mapper;

    public void send(ImageTranscription transcription, String topicName) {
        try {
            streamBridge.send(topicName, mapper.writeValueAsString(transcription));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}