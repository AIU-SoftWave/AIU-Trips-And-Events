package com.aiu.trips.service;

import com.aiu.trips.model.Payment;
import com.aiu.trips.model.Booking;
import com.aiu.trips.enums.PaymentStatus;
import com.aiu.trips.enums.PaymentMethod;
import com.aiu.trips.repository.PaymentRepository;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional
    public Payment processPayment(Long bookingId, PaymentMethod paymentMethod) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getAmountPaid());
        payment.setPaymentMethod(paymentMethod);
        payment.setTransactionId(generateTransactionId());
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
        
        Payment savedPayment = paymentRepository.save(payment);
        
        // Send payment confirmation notification
        notificationService.sendPaymentConfirmation(booking.getUser(), booking, savedPayment);
        
        return savedPayment;
    }
    
    @Transactional
    public Payment refundPayment(Long paymentId, String reason) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Only completed payments can be refunded");
        }
        
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundDate(LocalDateTime.now());
        payment.setRefundReason(reason);
        
        Payment refundedPayment = paymentRepository.save(payment);
        
        // Send refund notification
        notificationService.sendRefundNotification(payment.getBooking().getUser(), payment.getBooking(), refundedPayment);
        
        return refundedPayment;
    }
    
    public Payment getPaymentByBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return paymentRepository.findByBooking(booking)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found for this booking"));
    }
    
    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction ID: " + transactionId));
    }
    
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
