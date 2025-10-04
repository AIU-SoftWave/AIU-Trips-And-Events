package com.aiu.trips.repository;

import com.aiu.trips.model.EventFeedback;
import com.aiu.trips.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventFeedbackRepository extends JpaRepository<EventFeedback, Long> {
    List<EventFeedback> findByEvent(Event event);
    List<EventFeedback> findByEventId(Long eventId);
    
    @Query("SELECT AVG(f.rating) FROM EventFeedback f WHERE f.event.id = :eventId")
    Double getAverageRatingByEventId(Long eventId);
}
