package com.arques.generator.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CanvasToConsumerTopics {

    @Bean
    public NewTopic NewTopicDrawCapture(){
        return TopicBuilder.name(String.valueOf(Topics.DrawEvent))
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic NewTopicCaptureTravel(){
        return TopicBuilder.name(String.valueOf(Topics.TravelEvent))
                .partitions(1)
                .replicas(1)
                .build();
    }
}
