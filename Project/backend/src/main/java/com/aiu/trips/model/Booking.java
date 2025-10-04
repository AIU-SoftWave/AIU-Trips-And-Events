package com.aiu.trips.model;

import jakarta.persistence.*;
// Lombok temporarily removed due to Java 25 compatibility
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    
    public Booking() {}
    
    public Booking(Long id, User user, Event event, String bookingCode, String status, LocalDateTime bookingDate, String qrCodePath, Double amountPaid, String paymentMethod, LocalDateTime validatedAt, String validatedBy) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.bookingCode = bookingCode;
        this.status = status;
        this.bookingDate = bookingDate;
        this.qrCodePath = qrCodePath;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.validatedAt = validatedAt;
        this.validatedBy = validatedBy;
    }
    
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
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    
    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
    
    public String getQrCodePath() { return qrCodePath; }
    public void setQrCodePath(String qrCodePath) { this.qrCodePath = qrCodePath; }
    
    public Double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public LocalDateTime getValidatedAt() { return validatedAt; }
    public void setValidatedAt(LocalDateTime validatedAt) { this.validatedAt = validatedAt; }
    
    public String getValidatedBy() { return validatedBy; }
    public void setValidatedBy(String validatedBy) { this.validatedBy = validatedBy; }
}
