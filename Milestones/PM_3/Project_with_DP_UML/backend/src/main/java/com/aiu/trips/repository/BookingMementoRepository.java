package com.aiu.trips.repository;

import com.aiu.trips.pattern.memento.BookingMemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingMementoRepository extends JpaRepository<BookingMemento, Long> {
    List<BookingMemento> findByBookingIdOrderBySnapshotDateDesc(Long bookingId);
    Optional<BookingMemento> findFirstByBookingIdOrderBySnapshotDateDesc(Long bookingId);
}
