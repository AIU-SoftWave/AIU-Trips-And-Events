package com.aiu.trips.pattern.memento;

import com.aiu.trips.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * BookingMemento - Stores snapshot of Booking state for history/undo functionality
 */
@Entity
@Table(name = "booking_mementos")
public class BookingMemento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;
    
    @Column(name = "student_id", nullable = false)
    private String studentId;
    
    @Column(name = "activity_id", nullable = false)
    private Long activityId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
    
    @Column(name = "snapshot_date")
    private LocalDateTime snapshotDate;
    
    @PrePersist
    protected void onCreate() {
        if (snapshotDate == null) {
            snapshotDate = LocalDateTime.now();
        }
    }
    
    public BookingMemento() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public Long getActivityId() {
        return activityId;
    }
    
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
    
    public BookingStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getSnapshotDate() {
        return snapshotDate;
    }
    
    public void setSnapshotDate(LocalDateTime snapshotDate) {
        this.snapshotDate = snapshotDate;
    }
}
