package com.aiu.trips.model;

import com.aiu.trips.enums.ReportType;
import com.aiu.trips.enums.ExportFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportType type;
    
    @Column
    private String filePath;
    
    @Column(length = 1000)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "generated_by")
    private User generatedBy;
    
    @Column(nullable = false)
    private LocalDateTime generatedDate;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExportFormat format;
    
    // Constructors
    public Report() {}
    
    public Report(ReportType type, String filePath, String description, User generatedBy, ExportFormat format) {
        this.type = type;
        this.filePath = filePath;
        this.description = description;
        this.generatedBy = generatedBy;
        this.format = format;
        this.generatedDate = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        if (generatedDate == null) {
            generatedDate = LocalDateTime.now();
        }
    }
    
    // Getters and Setters
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
    
    public ReportType getType() { return type; }
    public void setType(ReportType type) { this.type = type; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public User getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(User generatedBy) { this.generatedBy = generatedBy; }
    
    public LocalDateTime getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; }
    
    public ExportFormat getFormat() { return format; }
    public void setFormat(ExportFormat format) { this.format = format; }
}
