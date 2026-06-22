package com.arques.generator.controller;

import java.util.List;


public record MouseMovementBatchRequest(List<MouseMovementEvent> events) {
}
