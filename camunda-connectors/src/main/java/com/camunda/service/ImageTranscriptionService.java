package com.camunda.service;

import com.camunda.dao.ImageTranscriptionDao;
import com.camunda.entity.ImageTranscription;
import com.camunda.model.TranscriptionStatus;
import com.camunda.producers.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Component
@Slf4j
public class ImageTranscriptionService {

    @Autowired
    private ImageTranscriptionDao transcriptionDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private KafkaProducer producer;

    @Bean
    public Consumer<String> transcribeImages() {
        return transcription -> {
            log.info("Received for transcription :: [{}]", transcription);
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readTree(transcription);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            ImageTranscription imageTranscription = mapper.convertValue(jsonNode.get("variablesAsMap"), ImageTranscription.class);
            transcriptionDao.save(imageTranscription);
            ImageTranscription imageTranscription1 = new ImageTranscription();
            BeanUtils.copyProperties(imageTranscription, imageTranscription1);
            imageTranscription1.setStatus(TranscriptionStatus.IMAGE_TRANSCRIBED);
            ImageTranscription imageTranscriptionDb = transcriptionDao.save(imageTranscription1);
            if (Integer.valueOf(imageTranscription.getCorrelationKey()) % 2 == 0) {
                log.info("Delayed");
            } else {
                log.info("Processed");
                producer.send(imageTranscriptionDb, "image-transcription-process-complete");
            }
        };
    }

    @Bean
    public Consumer<String> processTags() {
        return transcription -> {
            log.info("Received for transcription :: [{}]", transcription);
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readTree(transcription);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            ImageTranscription imageTranscription = mapper.convertValue(jsonNode.get("variablesAsMap"), ImageTranscription.class);
            imageTranscription.setStatus(TranscriptionStatus.TAGGING_COMPLETED);
            ImageTranscription imageTranscriptionDb = transcriptionDao.save(imageTranscription);
            log.info("Process completed :: [{}]", imageTranscriptionDb);
            producer.send(imageTranscriptionDb, "image-transcription-process-complete");
        };
    }
}
