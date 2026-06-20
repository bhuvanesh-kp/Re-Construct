package com.arques.construct.consumer;

import com.arques.construct.observer.MouseMovementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private final ReconstructionEventStore eventStore;

    public KafkaConsumer(ReconstructionEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @KafkaListener(id = "draw-listener", groupId = "test1", topics = "DrawEvent")
    public void listener(MouseMovementEvent event) {
        eventStore.add(event);
        log.info("messages received for DrawEvent: {}", event);
    }

    @KafkaListener(id = "travel-listener", groupId = "test1", topics = "TravelEvent")
    public void travelListener(MouseMovementEvent event) {
        eventStore.add(event);
        log.info("messages received for TravelEvent: {} {}", event.x(), event.y());
    }
}