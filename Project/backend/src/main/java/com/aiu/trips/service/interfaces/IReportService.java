package com.aiu.trips.service.interfaces;

import com.aiu.trips.model.Report;

import java.time.LocalDateTime;
import java.util.Map;

public interface IReportService {
    Map<String, Object> generateBookingReport(LocalDateTime startDate, LocalDateTime endDate);
    Map<String, Object> generateRevenueReport(LocalDateTime startDate, LocalDateTime endDate);
    Map<String, Object> generateUserActivityReport(Long userId);
    Map<String, Object> generateEventPopularityReport();
    Report exportReport(Long reportId, String format);
}
