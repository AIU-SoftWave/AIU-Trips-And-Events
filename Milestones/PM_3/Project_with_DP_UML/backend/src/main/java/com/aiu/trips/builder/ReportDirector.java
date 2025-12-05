package com.aiu.trips.builder;

import com.aiu.trips.dto.ReportDTO;
import com.aiu.trips.enums.ReportType;
import com.aiu.trips.factory.IModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Concrete Report Director
 * Implements report building orchestration
 */
@Component
public class ReportDirector implements IReportDirector {
    
    @Autowired
    private IModelFactory modelFactory;
    
    private ReportBuilder builder;
    
    public void setBuilder(ReportBuilder builder) {
        this.builder = builder;
    }
    
    @Override
    public ReportDTO makeParticipantsReport(List<?> data) {
        builder.reset();
        builder.buildHeader();
        builder.buildBody(data);
        builder.buildFooter();
        
        ReportDTO report = builder.getProduct();
        report.setType(ReportType.PARTICIPANTS);
        report.setDescription("Participants Report");
        return report;
    }
    
    @Override
    public ReportDTO makeRevenueReport(List<?> data) {
        builder.reset();
        builder.buildHeader();
        builder.buildBody(data);
        builder.buildFooter();
        
        ReportDTO report = builder.getProduct();
        report.setType(ReportType.REVENUE);
        report.setDescription("Revenue Report");
        return report;
    }
    
    @Override
    public ReportDTO makeFeedbackSummary(List<?> data) {
        builder.reset();
        builder.buildHeader();
        builder.buildBody(data);
        builder.buildFooter();
        
        ReportDTO report = builder.getProduct();
        report.setType(ReportType.FEEDBACK);
        report.setDescription("Feedback Summary Report");
        return report;
    }
}
