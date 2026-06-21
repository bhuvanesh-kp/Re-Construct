package com.arques.construct.observer;

import java.util.List;

import com.arques.construct.model.MouseMovementEvent;

public record MouseMovementBatchRequest(List<MouseMovementEvent> events) {
}
