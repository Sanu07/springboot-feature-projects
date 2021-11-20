package com.admin.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.admin.constants.AppConstants;

@Configuration
public class KafkaConfig {

	@Bean
    public NewTopic customerNotificationTopic(){
        return TopicBuilder.name(AppConstants.CONSUMER_NOTIFICATIONS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
	
	@Bean
    public NewTopic vendorNotificationTopic(){
        return TopicBuilder.name(AppConstants.VENDOR_NOTIFICATIONS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
