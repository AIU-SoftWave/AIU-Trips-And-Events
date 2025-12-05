package com.aiu.trips.repository;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByType(ActivityType type);
    List<Activity> findByStatus(ActivityStatus status);
    List<Activity> findByActivityDateAfter(LocalDateTime date);
    List<Activity> findByActivityDateBefore(LocalDateTime date);
    List<Activity> findByActivityDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Activity> findByOrganizer_Id(Long organizerId);
}
