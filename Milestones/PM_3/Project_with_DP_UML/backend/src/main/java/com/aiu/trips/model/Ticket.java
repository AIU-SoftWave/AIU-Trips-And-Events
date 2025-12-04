package com.aiu.trips.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Ticket entity - represents event/trip tickets
 * Based on After DP Data_Layer diagram
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "booking_id", nullable = false)
    private String bookingId;

    @Column(name = "qr_code", unique = true, nullable = false)
    private String qrCode;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @PrePersist
    protected void onCreate() {
        if (issueDate == null) {
            issueDate = LocalDateTime.now();
        }
        if (isUsed == null) {
            isUsed = false;
        }
    }

    // Constructors
    public Ticket() {}

    public Ticket(String ticketId, String bookingId, String qrCode, Boolean isUsed, LocalDateTime issueDate) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.qrCode = qrCode;
        this.isUsed = isUsed;
        this.issueDate = issueDate;
    }

    // Getters and Setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
}
