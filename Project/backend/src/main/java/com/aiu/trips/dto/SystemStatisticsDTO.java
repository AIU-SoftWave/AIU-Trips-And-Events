package com.aiu.trips.dto;

/**
 * SystemStatisticsDTO for transferring system statistics
 */
public class SystemStatisticsDTO {
    private Long totalUsers;
    private Long totalActivities;
    private Long totalBookings;
    private Long totalRevenue;
    private Double averageRating;

    public SystemStatisticsDTO() {}

    // Getters and Setters
    public Long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }

    public Long getTotalActivities() { return totalActivities; }
    public void setTotalActivities(Long totalActivities) { this.totalActivities = totalActivities; }

    public Long getTotalBookings() { return totalBookings; }
    public void setTotalBookings(Long totalBookings) { this.totalBookings = totalBookings; }

    public Long getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Long totalRevenue) { this.totalRevenue = totalRevenue; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
}
