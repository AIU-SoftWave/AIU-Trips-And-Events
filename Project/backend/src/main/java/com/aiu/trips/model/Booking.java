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
    private String status; // CONFIRMED, CANCELLED, ATTENDED
    
    @Column
    private LocalDateTime bookingDate;
    
    @Column(columnDefinition = "TEXT")
    private String qrCodePath;
    
    @Column
    private Double amountPaid;
    
    @Column
    private String paymentMethod; // CASH (default)
    
    @Column
    private LocalDateTime validatedAt;
    
    @Column
    private String validatedBy; // Email of staff who validated
    
    @PrePersist
    protected void onCreate() {
        bookingDate = LocalDateTime.now();
        status = "CONFIRMED";
        paymentMethod = "CASH";
    }
}
