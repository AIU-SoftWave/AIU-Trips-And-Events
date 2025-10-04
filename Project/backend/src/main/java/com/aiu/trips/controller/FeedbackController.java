package com.aiu.trips.controller;

import com.aiu.trips.model.EventFeedback;
import com.aiu.trips.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<EventFeedback> submitFeedback(
            @PathVariable Long eventId,
            @RequestBody EventFeedback feedback,
            Authentication authentication) {
        return ResponseEntity.ok(feedbackService.submitFeedback(eventId, feedback, authentication.getName()));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventFeedback>> getEventFeedback(@PathVariable Long eventId) {
        return ResponseEntity.ok(feedbackService.getEventFeedback(eventId));
    }
}
