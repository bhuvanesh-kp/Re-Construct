package com.arques.construct.Config.KafkaProducer;

import com.arques.construct.Config.Serializer.CustomSerializer;
import com.arques.construct.observer.MouseMovementCaptureResponse;
import com.arques.construct.observer.MouseMovementEvent;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.jackson.JsonValueSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {
    @Bean
    public ProducerFactory<String, MouseMovementEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        String bootstrapAddress = "localhost:9092";

        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class);
//        onfigProps.put(org.apache.kafka.clients.producer.ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG, 0);
//        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG, 100);c

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, MouseMovementEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
