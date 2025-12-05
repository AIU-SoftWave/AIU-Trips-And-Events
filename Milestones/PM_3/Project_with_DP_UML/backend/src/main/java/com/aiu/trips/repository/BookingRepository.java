package com.aiu.trips.repository;

import com.aiu.trips.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Id(Long userId);
    List<Booking> findByActivity_ActivityId(Long activityId);
    List<Booking> findByUser_IdAndActivity_ActivityId(Long userId, Long activityId);
    Optional<Booking> findByBookingCode(String bookingCode);
    boolean existsByUser_IdAndActivity_ActivityId(Long userId, Long activityId);
    
    // Backward compatibility methods (alias for activityId)
    @Query("SELECT b FROM Booking b WHERE b.activity.activityId = :eventId")
    List<Booking> findByEvent_Id(@Param("eventId") Long eventId);
    
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b WHERE b.user.id = :userId AND b.activity.activityId = :eventId")
    boolean existsByUser_IdAndEvent_Id(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
