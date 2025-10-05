package com.aiu.trips.repository;

import com.aiu.trips.model.Ticket;
import com.aiu.trips.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByBooking(Booking booking);
    Optional<Ticket> findByTicketCode(String ticketCode);
    Optional<Ticket> findByQrCode(String qrCode);
}
