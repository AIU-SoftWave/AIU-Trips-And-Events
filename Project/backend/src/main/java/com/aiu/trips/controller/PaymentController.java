package com.aiu.trips.controller;

import com.aiu.trips.model.Payment;
import com.aiu.trips.enums.PaymentMethod;
import com.aiu.trips.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<Payment> processPayment(
            @RequestParam Long bookingId,
            @RequestParam PaymentMethod paymentMethod) {
        return ResponseEntity.ok(paymentService.processPayment(bookingId, paymentMethod));
    }

    @PostMapping("/{paymentId}/refund")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<Payment> refundPayment(
            @PathVariable Long paymentId,
            @RequestParam String reason) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId, reason));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Payment> getPaymentByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentByBooking(bookingId));
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Payment> getPaymentByTransactionId(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.getPaymentByTransactionId(transactionId));
    }
}
