package com.vendor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.vendor.constants.AppConstants;

@Configuration
public class KafkaConfig {

	@Bean
    public NewTopic bookingTopic(){
        return TopicBuilder.name(AppConstants.VENDOR_RESPONSE_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
