package com.aiu.trips.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @Column(unique = true, nullable = false)
    private String bookingCode;
    
    @Column(nullable = false)
    private String status; // CONFIRMED, CANCELLED, ATTENDED, PENDING_PAYMENT
    
    @Column
    private LocalDateTime bookingDate;
    
    @Column
    private String qrCodePath;
    
    @Column
    private Double amountPaid;
    
    @Column
    private String paymentStatus; // PENDING, COMPLETED, FAILED, REFUNDED
    
    @Column
    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, CASH, ONLINE
    
    @Column
    private String transactionId;
    
    @Column
    private LocalDateTime paymentDate;
    
    @Column
    private Boolean ticketSent = false;
    
    @Column
    private Boolean attended = false;
    
    @Column
    private LocalDateTime attendedAt;
    
    @PrePersist
    protected void onCreate() {
        bookingDate = LocalDateTime.now();
        status = "PENDING_PAYMENT";
        paymentStatus = "PENDING";
        ticketSent = false;
        attended = false;
    }
}
