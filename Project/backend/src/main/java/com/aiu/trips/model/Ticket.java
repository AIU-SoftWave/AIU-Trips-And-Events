package com.aiu.trips.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    
    public Ticket() {}
    
    public Ticket(Long id, Booking booking, String ticketNumber, String qrCode, LocalDateTime issueDate, LocalDateTime validUntil, boolean isValid) {
        this.id = id;
        this.booking = booking;
        this.ticketNumber = ticketNumber;
        this.qrCode = qrCode;
        this.issueDate = issueDate;
        this.validUntil = validUntil;
        this.isValid = isValid;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    
    @Column(unique = true, nullable = false)
    private String ticketNumber;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String qrCode;
    
    @Column(nullable = false)
    private LocalDateTime issueDate;
    
    @Column
    private LocalDateTime validUntil;
    
    @Column(nullable = false)
    private boolean isValid;
    
    @PrePersist
    protected void onCreate() {
        issueDate = LocalDateTime.now();
        isValid = true;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    
    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }
    
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    
    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }
    
    public LocalDateTime getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }
    
    public boolean isValid() { return isValid; }
    public void setValid(boolean valid) { isValid = valid; }
}
