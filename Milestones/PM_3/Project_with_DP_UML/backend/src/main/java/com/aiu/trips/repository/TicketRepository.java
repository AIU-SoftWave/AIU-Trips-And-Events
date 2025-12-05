package com.aiu.trips.repository;

import com.aiu.trips.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByQrCode(String qrCode);
    Optional<Ticket> findByBooking_Id(Long bookingId);
}
