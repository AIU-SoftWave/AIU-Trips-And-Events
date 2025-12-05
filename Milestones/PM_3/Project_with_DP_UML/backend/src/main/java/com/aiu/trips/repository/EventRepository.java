package com.aiu.trips.repository;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTopic(String topic);
    List<Event> findByVenue(String venue);
    
    // Backward compatibility methods mapping to Activity fields
    @Query("SELECT e FROM Event e WHERE e.type = :type")
    List<Event> findByType(@Param("type") EventType type);
    
    @Query("SELECT e FROM Event e WHERE e.status = :status")
    List<Event> findByStatus(@Param("status") EventStatus status);
    
    @Query("SELECT e FROM Event e WHERE e.activityDate > :date")
    List<Event> findByStartDateAfter(@Param("date") LocalDateTime date);
    
    @Query("SELECT e FROM Event e WHERE e.organizer.id = :userId")
    List<Event> findByCreatedBy_Id(@Param("userId") Long userId);
}
