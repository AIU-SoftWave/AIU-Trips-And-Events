package com.aiu.trips.controller;

import com.aiu.trips.chain.RequestHandler;
import com.aiu.trips.command.*;
import com.aiu.trips.service.interfaces.IReportsAnalytics;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ReportController - Uses Command Pattern and Chain of Responsibility for all operations
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ControllerCommandInvoker commandInvoker;

    @Autowired
    private IReportsAnalytics reportService;

    @Autowired
    private RequestHandler handlerChain;

    @PostMapping("/generate")
    public ResponseEntity<?> generateReport(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        try {
            handlerChain.handle(request);
            IControllerCommand command = new GenerateReportCommand(reportService);
            commandInvoker.pushToQueue(command);
            return commandInvoker.executeNext(requestData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
