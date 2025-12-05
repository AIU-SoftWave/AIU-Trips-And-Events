package com.aiu.trips.repository;

import com.aiu.trips.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTopic(String topic);
    List<Event> findByVenue(String venue);
}
