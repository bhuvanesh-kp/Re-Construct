package com.arques.construct.observer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingMouseMovementProducer implements MouseMovementProducer {

	private static final Logger log = LoggerFactory.getLogger(LoggingMouseMovementProducer.class);

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
	}

	@Override
	public String name() {
		return "logging-placeholder";
	}
}
