package com.aiu.trips.dto.generated;

import java.time.LocalDateTime;

/**
 * Generated from PlantUML diagram
 * @generated
 */
public class Booking {

    private Long id;
    private Long userId;
    private Long eventId;
    private String bookingCode;
    private Integer numberOfSeats;
    private Double totalAmount;
    private String status;
    private String paymentMethod;
    private java.time.LocalDateTime bookingDate;
    private Boolean validated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public java.time.LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(java.time.LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

}
