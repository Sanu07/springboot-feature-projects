package com.consumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.consumer.constants.AppConstants;

@Configuration
public class KafkaConfig {

	@Bean
    public NewTopic bookingTopic(){
        return TopicBuilder.name(AppConstants.CONSUMER_SERVICE_BOOKING_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
