package com.aiu.trips.dto;

import com.aiu.trips.enums.ExportFormat;
import com.aiu.trips.enums.ReportType;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Report
 */
public class ReportDTO {
    private Long reportId;
    private ReportType type;
    private String filePath;
    private String description;
    private Long generatedBy;
    private LocalDateTime generatedDate;
    private ExportFormat format;
    private String content;
    
    // Constructor
    public ReportDTO() {}
    
    // Getters and Setters
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
    
    public ReportType getType() { return type; }
    public void setType(ReportType type) { this.type = type; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Long getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(Long generatedBy) { this.generatedBy = generatedBy; }
    
    public LocalDateTime getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; }
    
    public ExportFormat getFormat() { return format; }
    public void setFormat(ExportFormat format) { this.format = format; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
