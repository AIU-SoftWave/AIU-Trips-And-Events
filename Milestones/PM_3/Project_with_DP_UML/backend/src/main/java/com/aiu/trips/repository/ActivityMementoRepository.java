package com.aiu.trips.repository;

import com.aiu.trips.pattern.memento.ActivityMemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityMementoRepository extends JpaRepository<ActivityMemento, Long> {
    List<ActivityMemento> findByActivityIdOrderBySnapshotDateDesc(Long activityId);
    Optional<ActivityMemento> findFirstByActivityIdOrderBySnapshotDateDesc(Long activityId);
}
