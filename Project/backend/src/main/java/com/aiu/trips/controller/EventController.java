package com.aiu.trips.controller;

import com.aiu.trips.command.*;
import com.aiu.trips.service.interfaces.IActivityManagement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * EventController - Uses Command Pattern for all operations
 */
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private ControllerCommandInvoker commandInvoker;

    @Autowired
    private IActivityManagement activityService;

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        IControllerCommand command = new GetAllActivitiesCommand(activityService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(new HashMap<>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        // Using GetAllActivitiesCommand and filtering (simplified)
        return getAllEvents();
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Map<String, Object> eventData, Authentication authentication) {
        IControllerCommand command = new CreateEventCommand(activityService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(eventData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Map<String, Object> eventData) {
        eventData.put("id", id);
        IControllerCommand command = new UpdateEventCommand(activityService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(eventData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        IControllerCommand command = new DeleteEventCommand(activityService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(data);
    }
}
