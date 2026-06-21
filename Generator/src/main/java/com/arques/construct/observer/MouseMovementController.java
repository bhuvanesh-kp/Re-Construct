package com.arques.construct.observer;

import java.util.List;

import com.arques.construct.model.MouseMovementEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mouse-movements")
public class MouseMovementController {

	private final MouseMovementProducer producer;

	MouseMovementController(MouseMovementProducer producer) {
		this.producer = producer;
	}

	@PostMapping
	ResponseEntity<MouseMovementCaptureResponse> capture(@RequestBody(required = false) MouseMovementBatchRequest request) {
		List<MouseMovementEvent> events = request == null || request.events() == null
				? List.of()
				: request.events();

		producer.publish(events);

		return ResponseEntity.accepted().body(new MouseMovementCaptureResponse(events.size(), producer.name()));
	}
}
