package com.aiu.trips.builder;

import org.springframework.stereotype.Component;

/**
 * Builder Pattern - Concrete builder for PDF reports
 */
@Component
public class PdfReportBuilder extends ReportBuilder {
    
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
        // Build PDF-specific content
        StringBuilder pdfContent = new StringBuilder();
        pdfContent.append("PDF Report\n");
        pdfContent.append("==========\n\n");
        pdfContent.append(data);
        report.setContent(pdfContent.toString());
    }
    
    @Override
    public void setFormat() {
        report.setFormat("PDF");
    }
}
