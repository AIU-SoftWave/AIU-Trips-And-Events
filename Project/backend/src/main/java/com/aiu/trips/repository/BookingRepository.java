package com.aiu.trips.repository;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Event;
import com.aiu.trips.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Id(Long userId);
    List<Booking> findByEvent_Id(Long eventId);
    List<Booking> findByUser_IdAndEvent_Id(Long userId, Long eventId);
    List<Booking> findByEvent(Event event);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByBookingDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<Booking> findByBookingCode(String bookingCode);
    boolean existsByUser_IdAndEvent_Id(Long userId, Long eventId);
}
