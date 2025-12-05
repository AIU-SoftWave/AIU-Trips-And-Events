package com.aiu.trips.controller;

import com.aiu.trips.command.*;
import com.aiu.trips.service.interfaces.INotificationSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * NotificationController - Uses Command Pattern for all operations
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private ControllerCommandInvoker commandInvoker;

    @Autowired
    private INotificationSystem notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestBody Map<String, Object> requestData) {
        IControllerCommand command = new SendNotificationCommand(notificationService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(requestData);
    }
}
