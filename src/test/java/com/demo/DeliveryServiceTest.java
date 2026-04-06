package com.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryServiceTest {

    private DeliveryService service;

    @BeforeEach
    public void setUp() {
        service = new DeliveryService();
        service.addDelivery("TRK001");
        service.addDelivery("TRK002");
    }

    // Status update validation tests
    @Test
    public void testInitialStatusIsPickup() {
        assertEquals(DeliveryService.Status.PICKUP, service.getStatus("TRK001"));
    }

    @Test
    public void testUpdateToInTransit() {
        service.updateStatus("TRK001", DeliveryService.Status.IN_TRANSIT);
        assertEquals(DeliveryService.Status.IN_TRANSIT, service.getStatus("TRK001"));
    }

    @Test
    public void testUpdateToOutForDelivery() {
        service.updateStatus("TRK001", DeliveryService.Status.IN_TRANSIT);
        service.updateStatus("TRK001", DeliveryService.Status.OUT_FOR_DELIVERY);
        assertEquals(DeliveryService.Status.OUT_FOR_DELIVERY, service.getStatus("TRK001"));
    }

    @Test
    public void testUpdateToDelivered() {
        service.updateStatus("TRK001", DeliveryService.Status.IN_TRANSIT);
        service.updateStatus("TRK001", DeliveryService.Status.DELIVERED);
        assertEquals(DeliveryService.Status.DELIVERED, service.getStatus("TRK001"));
    }

    @Test
    public void testCancelDelivery() {
        service.updateStatus("TRK001", DeliveryService.Status.CANCELLED);
        assertEquals(DeliveryService.Status.CANCELLED, service.getStatus("TRK001"));
    }

    @Test
    public void testFullDeliveryFlow() {
        service.updateStatus("TRK002", DeliveryService.Status.IN_TRANSIT);
        service.updateStatus("TRK002", DeliveryService.Status.OUT_FOR_DELIVERY);
        service.updateStatus("TRK002", DeliveryService.Status.DELIVERED);
        assertEquals(DeliveryService.Status.DELIVERED, service.getStatus("TRK002"));
    }

    // Invalid status transition tests
    @Test
    public void testCannotUpdateAfterDelivered() {
        service.updateStatus("TRK001", DeliveryService.Status.DELIVERED);
        assertThrows(IllegalStateException.class, () ->
            service.updateStatus("TRK001", DeliveryService.Status.IN_TRANSIT));
    }

    @Test
    public void testCannotUpdateAfterCancelled() {
        service.updateStatus("TRK001", DeliveryService.Status.CANCELLED);
        assertThrows(IllegalStateException.class, () ->
            service.updateStatus("TRK001", DeliveryService.Status.IN_TRANSIT));
    }

    @Test
    public void testCannotGoBackToPickup() {
        service.updateStatus("TRK001", DeliveryService.Status.IN_TRANSIT);
        assertThrows(IllegalArgumentException.class, () ->
            service.updateStatus("TRK001", DeliveryService.Status.PICKUP));
    }

    @Test
    public void testTrackingIdNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
            service.updateStatus("GHOST123", DeliveryService.Status.IN_TRANSIT));
    }

    @Test
    public void testEmptyTrackingId() {
        assertThrows(IllegalArgumentException.class, () ->
            service.addDelivery(""));
    }

    @Test
    public void testDuplicateTrackingId() {
        assertThrows(IllegalStateException.class, () ->
            service.addDelivery("TRK001"));
    }

    @Test
    public void testGetStatusNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
            service.getStatus("NOTEXIST"));
    }
}