package com.demo;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Delivery Tracking System Started ===");
        DeliveryService service = new DeliveryService();
        service.addDelivery("TRK001");
        System.out.println(service.updateStatus("TRK001", DeliveryService.Status.IN_TRANSIT));
        System.out.println(service.updateStatus("TRK001", DeliveryService.Status.DELIVERED));
        System.out.println("=== System Running. Press Ctrl+C to stop. ===");
        try { Thread.currentThread().join(); } catch (InterruptedException ignored) {}
    }
}