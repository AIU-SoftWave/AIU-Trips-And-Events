package com.aiu.trips.repository;

import com.aiu.trips.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByType(String type);
    List<Event> findByCategory(String category);
    List<Event> findByStatus(String status);
    List<Event> findByStartDateAfter(LocalDateTime date);
    List<Event> findByCreatedBy_Id(Long userId);
}
