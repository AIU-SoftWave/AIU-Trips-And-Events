package com.aiu.trips.repository;

import com.aiu.trips.model.Report;
import com.aiu.trips.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByType(ReportType type);
    List<Report> findByGeneratedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Report> findByGeneratedBy(String generatedBy);
}
