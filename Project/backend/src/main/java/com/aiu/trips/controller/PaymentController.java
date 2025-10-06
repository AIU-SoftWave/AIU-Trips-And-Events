package com.aiu.trips.controller;

import com.aiu.trips.model.Payment;
import com.aiu.trips.enums.PaymentMethod;
import com.aiu.trips.enums.PaymentStatus;
import com.aiu.trips.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    /**
     * Process payment for a booking
     */
    @PostMapping("/process/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Payment> processPayment(
            @PathVariable Long bookingId,
            @RequestBody Map<String, String> request) {
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.get("paymentMethod"));
        Payment payment = paymentService.processPayment(bookingId, paymentMethod);
        return ResponseEntity.ok(payment);
    }
    
    /**
     * Refund a payment
     */
    @PostMapping("/refund/{paymentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long paymentId) {
        Payment payment = paymentService.refundPayment(paymentId);
        return ResponseEntity.ok(payment);
    }
    
    /**
     * Get payment by transaction ID
     */
    @GetMapping("/transaction/{transactionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Payment> getPaymentByTransactionId(@PathVariable String transactionId) {
        Payment payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(payment);
    }
    
    /**
     * Get payments by booking ID
     */
    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Payment>> getPaymentsByBookingId(@PathVariable Long bookingId) {
        List<Payment> payments = paymentService.getPaymentsByBookingId(bookingId);
        return ResponseEntity.ok(payments);
    }
    
    /**
     * Get payment status
     */
    @GetMapping("/{paymentId}/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, PaymentStatus>> getPaymentStatus(@PathVariable Long paymentId) {
        PaymentStatus status = paymentService.getPaymentStatus(paymentId);
        return ResponseEntity.ok(Map.of("status", status));
    }
}
