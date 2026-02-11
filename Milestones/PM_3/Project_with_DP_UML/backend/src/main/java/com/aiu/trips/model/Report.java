package com.aiu.trips.model;

import java.time.LocalDateTime;

/**
 * Simple Report model for demonstration purposes
 */
public class Report {
    
    private String reportId;
    private String title;
    private String content;
    private String format; // PDF, CSV
    private LocalDateTime generatedAt;
    
    public Report() {
        this.generatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
