package com.aiu.trips.controller;

import com.aiu.trips.command.*;
import com.aiu.trips.service.interfaces.IReportsAnalytics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ReportController - Uses Command Pattern for all operations
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ControllerCommandInvoker commandInvoker;

    @Autowired
    private IReportsAnalytics reportService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateReport(@RequestBody Map<String, Object> requestData) {
        IControllerCommand command = new GenerateReportCommand(reportService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(requestData);
    }
}
