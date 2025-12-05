package com.aiu.trips.builder;

import com.aiu.trips.dto.ReportDTO;
import com.aiu.trips.enums.ReportType;

import java.util.List;

/**
 * Report Director Interface
 * Orchestrates report building process
 */
public interface IReportDirector {
    /**
     * Makes a participants report
     * @param data Report data
     * @return Built report
     */
    ReportDTO makeParticipantsReport(List<?> data);
    
    /**
     * Makes a revenue report
     * @param data Report data
     * @return Built report
     */
    ReportDTO makeRevenueReport(List<?> data);
    
    /**
     * Makes a feedback summary report
     * @param data Report data
     * @return Built report
     */
    ReportDTO makeFeedbackSummary(List<?> data);
}
