package com.arques.generator.controller;

import java.util.List;


public interface MouseMovementProducer {

	void publish(List<MouseMovementEvent> events);

	String name();
}
