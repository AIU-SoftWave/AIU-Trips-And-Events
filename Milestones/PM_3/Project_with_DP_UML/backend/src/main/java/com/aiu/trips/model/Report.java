package com.aiu.trips.model;

import com.aiu.trips.enums.ExportFormat;
import com.aiu.trips.enums.ReportType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Report entity - represents generated reports
 */
@Entity
@Table(name = "reports")
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type;
    
    @Column(name = "file_path", length = 1000)
    private String filePath;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "generated_by")
    private String generatedBy;
    
    @Column(name = "generated_date")
    private LocalDateTime generatedDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExportFormat format;
    
    @Lob
    @Column(name = "content")
    private byte[] content;
    
    @PrePersist
    protected void onCreate() {
        if (generatedDate == null) {
            generatedDate = LocalDateTime.now();
        }
    }
    
    public Report() {}
    
    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }
    
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
    
    public ReportType getType() {
        return type;
    }
    
    public void setType(ReportType type) {
        this.type = type;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getGeneratedBy() {
        return generatedBy;
    }
    
    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }
    
    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }
    
    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }
    
    public ExportFormat getFormat() {
        return format;
    }
    
    public void setFormat(ExportFormat format) {
        this.format = format;
    }
    
    public byte[] getContent() {
        return content;
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }
}
