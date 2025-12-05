package com.aiu.trips.service;

import com.aiu.trips.dto.FeedbackDTO;
import com.aiu.trips.dto.ReportDTO;
import com.aiu.trips.dto.ReportFilterDTO;
import com.aiu.trips.dto.SystemStatisticsDTO;
import com.aiu.trips.enums.ExportFormat;
import com.aiu.trips.enums.ReportType;
import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Event;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.service.interfaces.IReportsAnalytics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService implements IReportsAnalytics {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Map<String, Object> getEventReport(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);

        Map<String, Object> report = new HashMap<>();
        report.put("eventId", event.getId());
        report.put("eventTitle", event.getTitle());
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
        List<Event> events = eventRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        Map<String, Object> report = new HashMap<>();
        report.put("totalEvents", events.size());
        report.put("totalBookings", bookings.size());
        report.put("totalIncome", bookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .mapToDouble(Booking::getAmountPaid)
            .sum());
        report.put("activeEvents", events.stream()
            .filter(e -> "ACTIVE".equals(e.getStatus()))
            .count());
        report.put("completedEvents", events.stream()
            .filter(e -> "COMPLETED".equals(e.getStatus()))
            .count());

        return report;
    }

    @Override
    public ReportDTO generateReport(ReportType reportType, ReportFilterDTO filters, ExportFormat format) {
        // Placeholder implementation
        return new ReportDTO();
    }

    @Override
    public byte[] exportReport(Long reportId, ExportFormat format) {
        // Placeholder implementation
        return new byte[0];
    }

    @Override
    public SystemStatisticsDTO getStatistics() {
        // Placeholder implementation
        return new SystemStatisticsDTO();
    }

    @Override
    public void collectFeedback(FeedbackDTO feedbackData) {
        // Placeholder implementation
    }
}
