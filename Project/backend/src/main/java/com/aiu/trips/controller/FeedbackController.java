package com.aiu.trips.controller;

import com.aiu.trips.model.Feedback;
import com.aiu.trips.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Feedback> createFeedback(
            @PathVariable Long eventId,
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        Integer rating = (Integer) request.get("rating");
        String comment = (String) request.get("comment");
        
        return ResponseEntity.ok(
            feedbackService.createFeedback(eventId, rating, comment, authentication.getName())
        );
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Feedback>> getEventFeedbacks(@PathVariable Long eventId) {
        return ResponseEntity.ok(feedbackService.getEventFeedbacks(eventId));
    }

    @GetMapping("/my-feedbacks")
    public ResponseEntity<List<Feedback>> getMyFeedbacks(Authentication authentication) {
        return ResponseEntity.ok(feedbackService.getUserFeedbacks(authentication.getName()));
    }

    @GetMapping("/event/{eventId}/average-rating")
    public ResponseEntity<Map<String, Double>> getEventAverageRating(@PathVariable Long eventId) {
        Map<String, Double> response = new HashMap<>();
        response.put("averageRating", feedbackService.getEventAverageRating(eventId));
        return ResponseEntity.ok(response);
    }
}
