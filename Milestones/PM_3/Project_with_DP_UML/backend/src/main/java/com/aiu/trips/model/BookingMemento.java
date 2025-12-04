package com.aiu.trips.model;

import com.aiu.trips.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * BookingMemento - snapshot of Booking state for Memento pattern
 * Based on After DP Data_Layer diagram
 */
@Entity
@Table(name = "booking_mementos")
public class BookingMemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private String bookingId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "activity_id", nullable = false)
    private String activityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        if (savedAt == null) {
            savedAt = LocalDateTime.now();
        }
    }

    // Constructors
    public BookingMemento() {}

    public BookingMemento(String bookingId, String studentId, String activityId, BookingStatus status) {
        this.bookingId = bookingId;
        this.studentId = studentId;
        this.activityId = activityId;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
