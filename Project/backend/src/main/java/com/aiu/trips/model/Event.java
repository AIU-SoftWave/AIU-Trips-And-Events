package com.aiu.trips.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String type; // EVENT, TRIP
    
    @Column
    private String category; // FIELD_TRIP, SEMINAR, CONFERENCE, CONCERT
    
    @Column(nullable = false)
    private LocalDateTime startDate;
    
    @Column
    private LocalTime startTime;
    
    @Column
    private LocalDateTime endDate;
    
    @Column
    private String location;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(nullable = false)
    private Integer availableSeats;
    
    @Column
    private LocalDateTime registrationDeadline;
    
    @Column
    private String imageUrl;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column
    private LocalDateTime createdAt;
    
    @Column
    private String status; // ACTIVE, CANCELLED, COMPLETED
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        availableSeats = capacity;
        status = "ACTIVE";
    }
}
