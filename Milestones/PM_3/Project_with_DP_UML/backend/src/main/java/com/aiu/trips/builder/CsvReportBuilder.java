package com.aiu.trips.builder;

import org.springframework.stereotype.Component;

/**
 * Builder Pattern - Concrete builder for CSV reports
 */
@Component
public class CsvReportBuilder extends ReportBuilder {
    
    @Override
    public void setReportId(String reportId) {
        report.setReportId(reportId);
    }
    
    @Override
    public void setTitle(String title) {
        report.setTitle(title);
    }
    
    @Override
    public void buildContent(String data) {
        // Build CSV-specific content
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("# CSV Report: ").append(report.getTitle()).append("\n");
        csvContent.append(data);
        report.setContent(csvContent.toString());
    }
    
    @Override
    public void setFormat() {
        report.setFormat("CSV");
    }
}
