package com.arques.construct.observer;

import java.util.List;

public interface MouseMovementProducer {

	void publish(List<MouseMovementEvent> events);

	String name();
}
