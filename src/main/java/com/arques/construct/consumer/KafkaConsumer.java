package com.arques.construct.consumer;

import com.arques.construct.config.Topic.Topics;
import com.arques.construct.observer.MouseMovementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(id = "draw-listener", groupId = "test1", topics = "DrawEvent") // here I am not able to store it as constant value or use toString from Topics.DrawEvent
    public void Listener(MouseMovementEvent event){
        log.info("messages received for DrawEvent: {} ", event.toString());
        System.out.println(event);
    }

    @KafkaListener(id = "travel-listener", groupId = "test1", topics = "TravelEvent") // need to find out how I can use values from Enum Topics so I am not hardCoding values here
    public void TravelListener(MouseMovementEvent event){
        log.info("messages received for TravelEvent: {} {}", event.x(), event.y());
        System.out.println(event);
    }
}
