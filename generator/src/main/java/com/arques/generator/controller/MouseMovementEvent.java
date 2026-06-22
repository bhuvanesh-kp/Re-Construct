package com.arques.generator.controller;

import com.arques.construct.model.CanvasState;

public record MouseMovementEvent(
		String sessionId,
		long occurredAt,
		int sequence,
		double x,
		double y,
		double viewportWidth,
		double viewportHeight,
		String path,
		String eventType,
		CanvasState state) {
}
