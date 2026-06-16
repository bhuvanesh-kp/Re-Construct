package com.arques.construct.observer;

public record MouseMovementEvent(
		String sessionId,
		long occurredAt,
		int sequence,
		double x,
		double y,
		double viewportWidth,
		double viewportHeight,
		String path,
		String eventType) {
}
