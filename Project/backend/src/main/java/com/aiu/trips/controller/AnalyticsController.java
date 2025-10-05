package com.aiu.trips.controller;

import com.aiu.trips.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/system")
    public ResponseEntity<Map<String, Object>> getSystemStatistics() {
        return ResponseEntity.ok(analyticsService.getSystemStatistics());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Map<String, Object>> getEventStatistics(@PathVariable Long eventId) {
        return ResponseEntity.ok(analyticsService.getEventStatistics(eventId));
    }

    @GetMapping("/engagement")
    public ResponseEntity<Map<String, Object>> getUserEngagement() {
        return ResponseEntity.ok(analyticsService.getUserEngagement());
    }

    @GetMapping("/revenue")
    public ResponseEntity<Map<String, Object>> getRevenueAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(analyticsService.getRevenueAnalytics(startDate, endDate));
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Long>> getEventCategoryDistribution() {
        return ResponseEntity.ok(analyticsService.getEventCategoryDistribution());
    }
}
