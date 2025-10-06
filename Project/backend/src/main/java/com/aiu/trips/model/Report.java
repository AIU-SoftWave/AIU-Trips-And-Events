package com.aiu.trips.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    
    public Report() {}
    
    public Report(Long id, String reportType, User generatedBy, LocalDateTime generatedAt, String data, String format) {
        this.id = id;
        this.reportType = reportType;
        this.generatedBy = generatedBy;
        this.generatedAt = generatedAt;
        this.data = data;
        this.format = format;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String reportType;
    
    @ManyToOne
    @JoinColumn(name = "generated_by")
    private User generatedBy;
    
    @Column(nullable = false)
    private LocalDateTime generatedAt;
    
    @Column(columnDefinition = "TEXT")
    private String data;
    
    @Column
    private String format;
    
    @PrePersist
    protected void onCreate() {
        generatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    
    public User getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(User generatedBy) { this.generatedBy = generatedBy; }
    
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
}
