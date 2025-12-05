package com.aiu.trips.repository;

import com.aiu.trips.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByActivity_ActivityId(Long activityId);
    List<Feedback> findByUser_Id(Long userId);
    boolean existsByUser_IdAndActivity_ActivityId(Long userId, Long activityId);
    
    // Backward compatibility methods (alias for activityId)
    @Query("SELECT f FROM Feedback f WHERE f.activity.activityId = :eventId")
    List<Feedback> findByEvent_Id(@Param("eventId") Long eventId);
    
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Feedback f WHERE f.user.id = :userId AND f.activity.activityId = :eventId")
    boolean existsByUser_IdAndEvent_Id(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
