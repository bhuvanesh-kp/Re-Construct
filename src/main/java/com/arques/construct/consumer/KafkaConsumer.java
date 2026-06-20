package com.arques.construct.consumer;

import com.arques.construct.observer.MouseMovementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(id = "test1", topics = "topic1")
    public void Listener(MouseMovementEvent event){
        log.info("messages received : {} ", event.toString());
        System.out.println(event);
    }
}
