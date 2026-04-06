package com.demo;

import java.util.HashMap;
import java.util.Map;

public class DeliveryService {

    public enum Status {
        PICKUP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }

    private Map<String, Status> deliveries = new HashMap<>();

    public void addDelivery(String trackingId) {
        if (trackingId == null || trackingId.trim().isEmpty())
            throw new IllegalArgumentException("Tracking ID cannot be empty");
        if (deliveries.containsKey(trackingId))
            throw new IllegalStateException("Tracking ID already exists: " + trackingId);
        deliveries.put(trackingId, Status.PICKUP);
    }

    public String updateStatus(String trackingId, Status newStatus) {
        if (trackingId == null || trackingId.trim().isEmpty())
            throw new IllegalArgumentException("Tracking ID cannot be empty");
        if (!deliveries.containsKey(trackingId))
            throw new IllegalArgumentException("Tracking ID not found: " + trackingId);

        Status current = deliveries.get(trackingId);

        // Invalid status transitions
        if (current == Status.DELIVERED)
            throw new IllegalStateException("Cannot update: already delivered");
        if (current == Status.CANCELLED)
            throw new IllegalStateException("Cannot update: delivery is cancelled");
        if (newStatus == Status.PICKUP)
            throw new IllegalArgumentException("Cannot go back to PICKUP status");

        deliveries.put(trackingId, newStatus);
        return trackingId + " status updated to: " + newStatus;
    }

    public Status getStatus(String trackingId) {
        if (!deliveries.containsKey(trackingId))
            throw new IllegalArgumentException("Tracking ID not found: " + trackingId);
        return deliveries.get(trackingId);
    }

    public Map<String, Status> getAllDeliveries() {
        return deliveries;
    }
}