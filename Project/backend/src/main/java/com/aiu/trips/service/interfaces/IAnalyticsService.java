package com.aiu.trips.service.interfaces;

import java.util.Map;

public interface IAnalyticsService {
    Map<String, Object> getEventStatistics(Long eventId);
    Map<String, Object> getUserEngagementMetrics();
    Map<String, Object> getTrendAnalysis(String period);
    Map<String, Object> getBookingPatterns();
}
