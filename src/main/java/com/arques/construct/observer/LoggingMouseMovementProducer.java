package com.arques.construct.observer;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggingMouseMovementProducer implements MouseMovementProducer {

	private static final Logger log = LoggerFactory.getLogger(LoggingMouseMovementProducer.class);
	private final KafkaTemplate<String, MouseMovementEvent> kafkaTemplate;

	@Override
	public void publish(List<MouseMovementEvent> events) {
		if (events.isEmpty()) {
			return;
		}

		MouseMovementEvent first = events.getFirst();
		MouseMovementEvent last = events.getLast();
		log.info(
				"Captured {} mouse movement event(s) for session {} from sequence {} to {}",
				events.size(),
				first.sessionId(),
				first.sequence(),
				last.sequence());

		for(MouseMovementEvent event : events) {
			kafkaTemplate.send("topic1", event);
		}

	}

	@Override
	public String name() {
		return "logging-placeholder";
	}
}
