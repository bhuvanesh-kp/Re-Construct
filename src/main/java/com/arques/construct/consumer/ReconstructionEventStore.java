package com.arques.construct.consumer;

import com.arques.construct.observer.MouseMovementEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReconstructionEventStore {
    private static final int MAX_EVENTS = 2_000;
    private final List<MouseMovementEvent> events = new ArrayList<>();

    public synchronized void add(MouseMovementEvent event) {
        events.add(event);
        if (events.size() > MAX_EVENTS) {
            events.remove(0);
        }
    }

    public synchronized List<MouseMovementEvent> snapshot() {
        return List.copyOf(events);
    }

    public synchronized void clear() {
        events.clear();
    }
}