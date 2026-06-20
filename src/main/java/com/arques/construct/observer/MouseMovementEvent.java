package com.arques.construct.observer;

import com.arques.construct.DTO.State.CanvasState;

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