package com.aiu.trips.builder;

import com.aiu.trips.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Builder Pattern - Director class for Report construction
 * Orchestrates the building process using report builders
 */
@Component
public class ReportDirector {
    
    @Autowired
    private PdfReportBuilder pdfReportBuilder;
    
    @Autowired
    private CsvReportBuilder csvReportBuilder;
    
    public Report constructPdfReport(String reportId, String title, String data) {
        pdfReportBuilder.reset();
        pdfReportBuilder.setReportId(reportId);
        pdfReportBuilder.setTitle(title);
        pdfReportBuilder.buildContent(data);
        pdfReportBuilder.setFormat();
        return pdfReportBuilder.getReport();
    }
    
    public Report constructCsvReport(String reportId, String title, String data) {
        csvReportBuilder.reset();
        csvReportBuilder.setReportId(reportId);
        csvReportBuilder.setTitle(title);
        csvReportBuilder.buildContent(data);
        csvReportBuilder.setFormat();
        return csvReportBuilder.getReport();
    }
}
