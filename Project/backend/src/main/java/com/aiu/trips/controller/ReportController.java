package com.aiu.trips.controller;

import com.aiu.trips.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Map<String, Object>> getEventReport(@PathVariable Long eventId) {
        return ResponseEntity.ok(reportService.getEventReport(eventId));
    }

    @GetMapping("/overall")
    public ResponseEntity<Map<String, Object>> getOverallReport() {
        return ResponseEntity.ok(reportService.getOverallReport());
    }
    
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<Map<String, Object>> getOrganizerPerformance(@PathVariable Long organizerId) {
        return ResponseEntity.ok(reportService.getOrganizerPerformance(organizerId));
    }
    
    @GetMapping("/attendance/{eventId}")
    public ResponseEntity<Map<String, Object>> getAttendanceReport(@PathVariable Long eventId) {
        return ResponseEntity.ok(reportService.getAttendanceReport(eventId));
    }
}
