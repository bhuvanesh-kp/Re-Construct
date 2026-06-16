package com.arques.construct.observer;

import java.util.List;

public record MouseMovementBatchRequest(List<MouseMovementEvent> events) {
}
