package com.aiu.trips.model;

import com.aiu.trips.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Memento Pattern: Snapshot of Booking state
 * Stores historical state for undo/rollback functionality
 */
@Entity
@Table(name = "booking_mementos")
public class BookingMemento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mementoId;
    
    @Column(nullable = false)
    private Long bookingId;
    
    @Column
    private Long studentId;
    
    @Column
    private Long activityId;
    
    @Column
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    @Column
    private LocalDateTime snapshotDate;
    
    // Constructors
    public BookingMemento() {
        this.snapshotDate = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        if (snapshotDate == null) {
            snapshotDate = LocalDateTime.now();
        }
    }
    
    // Getters and Setters
    public Long getMementoId() { return mementoId; }
    public void setMementoId(Long mementoId) { this.mementoId = mementoId; }
    
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    
    public LocalDateTime getSnapshotDate() { return snapshotDate; }
    public void setSnapshotDate(LocalDateTime snapshotDate) { this.snapshotDate = snapshotDate; }
}
