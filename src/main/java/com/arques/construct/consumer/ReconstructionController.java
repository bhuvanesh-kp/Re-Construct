package com.arques.construct.consumer;

import com.arques.construct.observer.MouseMovementEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reconstruction")
public class ReconstructionController {
    private final ReconstructionEventStore eventStore;

    public ReconstructionController(ReconstructionEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @GetMapping("/events")
    public List<MouseMovementEvent> events() {
        return eventStore.snapshot();
    }

    @DeleteMapping("/events")
    public ResponseEntity<Void> clear() {
        eventStore.clear();
        return ResponseEntity.noContent().build();
    }
}