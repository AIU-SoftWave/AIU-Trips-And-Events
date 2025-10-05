package com.aiu.trips.controller;

import com.aiu.trips.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/reports")
@PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
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
    
    @GetMapping("/event/{eventId}/export/csv")
    public ResponseEntity<byte[]> exportEventReportAsCSV(@PathVariable Long eventId) {
        byte[] csvData = reportService.exportEventReportAsCSV(eventId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "event-report-" + eventId + ".csv");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(csvData);
    }
    
    @GetMapping("/overall/export/csv")
    public ResponseEntity<byte[]> exportOverallReportAsCSV() {
        byte[] csvData = reportService.exportOverallReportAsCSV();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "overall-report.csv");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(csvData);
    }
    
    @GetMapping("/event/{eventId}/export/pdf")
    public ResponseEntity<String> exportEventReportAsPDF(@PathVariable Long eventId) {
        String pdfMessage = reportService.exportEventReportAsPDF(eventId);
        return ResponseEntity.ok(pdfMessage);
    }
    
    @GetMapping("/overall/export/pdf")
    public ResponseEntity<String> exportOverallReportAsPDF() {
        String pdfMessage = reportService.exportOverallReportAsPDF();
        return ResponseEntity.ok(pdfMessage);
    }
}

