package com.aiu.trips.service;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Event;
import com.aiu.trips.enums.BookingStatus;
import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
            .mapToDouble(Booking::getAmountPaid)
            .sum());
        report.put("cancelledBookings", bookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
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
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
            .mapToDouble(Booking::getAmountPaid)
            .sum());
        report.put("activeEvents", events.stream()
            .filter(e -> e.getStatus() == EventStatus.ACTIVE)
            .count());
        report.put("completedEvents", events.stream()
            .filter(e -> e.getStatus() == EventStatus.COMPLETED)
            .count());

        return report;
    }
    
    public byte[] exportEventReportAsCSV(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        
        // CSV Header
        writer.println("Event Report");
        writer.println("Event Title," + event.getTitle());
        writer.println("Event Date," + event.getStartDate().format(DATE_FORMATTER));
        writer.println("Location," + event.getLocation());
        writer.println("Total Capacity," + event.getCapacity());
        writer.println("Available Seats," + event.getAvailableSeats());
        writer.println();
        
        // Bookings Header
        writer.println("Booking Code,User Email,User Name,Booking Date,Amount Paid,Status");
        
        // Bookings Data
        for (Booking booking : bookings) {
            writer.printf("%s,%s,%s,%s,%.2f,%s%n",
                booking.getBookingCode(),
                booking.getUser().getEmail(),
                booking.getUser().getFullName(),
                booking.getBookingDate().format(DATE_FORMATTER),
                booking.getAmountPaid(),
                booking.getStatus()
            );
        }
        
        writer.flush();
        writer.close();
        
        return outputStream.toByteArray();
    }
    
    public byte[] exportOverallReportAsCSV() {
        List<Event> events = eventRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        
        // Overall Statistics
        writer.println("Overall Report");
        writer.println("Generated At," + LocalDateTime.now().format(DATE_FORMATTER));
        writer.println();
        
        writer.println("Total Events," + events.size());
        writer.println("Total Bookings," + bookings.size());
        writer.println("Total Income," + String.format("%.2f", bookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
            .mapToDouble(Booking::getAmountPaid)
            .sum()));
        writer.println();
        
        // Events Header
        writer.println("Event ID,Event Title,Type,Start Date,Location,Capacity,Available Seats,Status");
        
        // Events Data
        for (Event event : events) {
            writer.printf("%d,%s,%s,%s,%s,%d,%d,%s%n",
                event.getId(),
                event.getTitle(),
                event.getType(),
                event.getStartDate().format(DATE_FORMATTER),
                event.getLocation(),
                event.getCapacity(),
                event.getAvailableSeats(),
                event.getStatus()
            );
        }
        
        writer.flush();
        writer.close();
        
        return outputStream.toByteArray();
    }
    
    public String exportEventReportAsPDF(Long eventId) {
        // For PDF generation, we would typically use libraries like iText or Apache PDFBox
        // For now, returning a placeholder message indicating PDF export capability
        Map<String, Object> report = getEventReport(eventId);
        return "PDF Export functionality would generate a PDF report for event: " + report.get("eventTitle");
    }
    
    public String exportOverallReportAsPDF() {
        // For PDF generation, we would typically use libraries like iText or Apache PDFBox
        // For now, returning a placeholder message indicating PDF export capability
        return "PDF Export functionality would generate an overall system report";
    }
}
