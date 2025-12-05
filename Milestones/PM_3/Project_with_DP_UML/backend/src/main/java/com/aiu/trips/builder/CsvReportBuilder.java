package com.aiu.trips.builder;

import com.aiu.trips.dto.ReportDTO;
import com.aiu.trips.enums.ExportFormat;
import com.aiu.trips.factory.IModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Concrete Report Builder for CSV format
 */
@Component
public class CsvReportBuilder extends ReportBuilder {
    
    private ReportDTO report;
    
    @Autowired
    private IModelFactory modelFactory;
    
    @Override
    public void reset() {
        this.report = new ReportDTO();
        this.report.setFormat(ExportFormat.CSV);
        this.report.setGeneratedDate(LocalDateTime.now());
    }
    
    @Override
    public void buildHeader() {
        String header = "Report,Generated," + LocalDateTime.now() + "\n";
        header += "Column1,Column2,Column3\n";
        report.setContent(header);
    }
    
    @Override
    public void buildBody(List<?> data) {
        String body = report.getContent() != null ? report.getContent() : "";
        for (Object item : data) {
            body += item.toString() + "\n";
        }
        report.setContent(body);
    }
    
    @Override
    public void buildFooter() {
        // CSV doesn't typically need a footer
        String content = report.getContent() != null ? report.getContent() : "";
        content += "END\n";
        report.setContent(content);
    }
    
    @Override
    public ReportDTO getProduct() {
        ReportDTO result = this.report;
        reset();
        return result;
    }
}
