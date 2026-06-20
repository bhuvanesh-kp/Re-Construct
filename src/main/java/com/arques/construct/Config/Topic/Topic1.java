package com.arques.construct.config.Topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Topic1 {

    @Bean
    public NewTopic NewTopic(){
        return TopicBuilder.name("topic1")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
