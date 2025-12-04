package com.aiu.trips.controller;

import com.aiu.trips.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/reports")
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
}
