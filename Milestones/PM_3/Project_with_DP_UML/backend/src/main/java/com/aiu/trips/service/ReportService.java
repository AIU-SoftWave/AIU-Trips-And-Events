package com.aiu.trips.service;

import com.aiu.trips.model.Activity;
import com.aiu.trips.model.Booking;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Map<String, Object> getEventReport(Long eventId) {
        Activity event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);

        Map<String, Object> report = new HashMap<>();
        report.put("eventId", event.getActivityId());
        report.put("eventTitle", event.getName());
        report.put("totalCapacity", event.getCapacity());
        report.put("availableSeats", event.getAvailableSeats());
        report.put("bookedSeats", event.getCapacity() - event.getAvailableSeats());
        report.put("totalParticipants", bookings.size());
        report.put("totalIncome", bookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .mapToDouble(Booking::getAmountPaid)
            .sum());
        report.put("cancelledBookings", bookings.stream()
            .filter(b -> "CANCELLED".equals(b.getStatus()))
            .count());

        return report;
    }

    public Map<String, Object> getOverallReport() {
        List<Activity> events = eventRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        Map<String, Object> report = new HashMap<>();
        report.put("totalEvents", events.size());
        report.put("totalBookings", bookings.size());
        report.put("totalIncome", bookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .mapToDouble(Booking::getAmountPaid)
            .sum());
        report.put("activeEvents", events.stream()
            .filter(e -> "UPCOMING".equals(e.getStatus().name()))
            .count());
        report.put("completedEvents", events.stream()
            .filter(e -> "COMPLETED".equals(e.getStatus().name()))
            .count());

        return report;
    }
}
