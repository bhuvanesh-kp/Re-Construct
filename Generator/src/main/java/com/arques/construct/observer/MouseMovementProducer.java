package com.arques.construct.observer;

import java.util.List;

import com.arques.construct.model.MouseMovementEvent;

public interface MouseMovementProducer {

	void publish(List<MouseMovementEvent> events);

	String name();
}
